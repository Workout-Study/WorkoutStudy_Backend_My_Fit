package com.fitmate.myfit.application.service.service

import com.fitmate.myfit.adapter.`in`.web.certification.response.FitCertificationProgressesResponse
import com.fitmate.myfit.application.port.`in`.certification.command.*
import com.fitmate.myfit.application.port.`in`.certification.response.*
import com.fitmate.myfit.application.port.`in`.certification.usecase.DeleteFitCertificationUseCase
import com.fitmate.myfit.application.port.`in`.certification.usecase.ReadFitCertificationUseCase
import com.fitmate.myfit.application.port.`in`.certification.usecase.RegisterFitCertificationUseCase
import com.fitmate.myfit.application.port.`in`.certification.usecase.UpdateFitCertificationResultUseCase
import com.fitmate.myfit.application.port.`in`.my.fit.command.FitCertificationFilterByGroupCommand
import com.fitmate.myfit.application.port.out.certification.*
import com.fitmate.myfit.application.port.out.fit.group.ReadFitGroupForReadPort
import com.fitmate.myfit.application.port.out.fit.mate.ReadFitMateForReadPort
import com.fitmate.myfit.application.port.out.fit.record.ReadFitRecordPort
import com.fitmate.myfit.application.port.out.fit.record.ReadRecordMultiMediaEndPointPort
import com.fitmate.myfit.application.port.out.user.ReadUserForReadPort
import com.fitmate.myfit.application.port.out.vote.ReadVotePort
import com.fitmate.myfit.application.service.converter.FitCertificationUseCaseConverter
import com.fitmate.myfit.common.GlobalStatus
import com.fitmate.myfit.common.exceptions.BadRequestException
import com.fitmate.myfit.common.exceptions.ResourceAlreadyExistException
import com.fitmate.myfit.common.exceptions.ResourceNotFoundException
import com.fitmate.myfit.domain.CertificationStatus
import com.fitmate.myfit.domain.FitCertification
import com.fitmate.myfit.domain.UserForRead
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import kotlin.jvm.optionals.getOrNull

@Service
class FitCertificationService(
    private val readFitRecordPort: ReadFitRecordPort,
    private val readFitCertificationPort: ReadFitCertificationPort,
    private val registerFitCertificationPort: RegisterFitCertificationPort,
    private val updateFitCertificationPort: UpdateFitCertificationPort,
    private val readFitGroupForReadPort: ReadFitGroupForReadPort,
    private val readFitMateForReadPort: ReadFitMateForReadPort,
    private val fitCertificationEventPublishPort: FitCertificationEventPublishPort,
    private val readVotePort: ReadVotePort,
    private val readFitCertificationResultPort: ReadFitCertificationResultPort,
    private val readRecordMultiMediaEndPointPort: ReadRecordMultiMediaEndPointPort,
    private val readUserForReadPort: ReadUserForReadPort
) : RegisterFitCertificationUseCase, DeleteFitCertificationUseCase, ReadFitCertificationUseCase,
    UpdateFitCertificationResultUseCase {

    /**
     * Register fit certification use case,
     * register fit certification data to persistence
     *
     * @param registerFitCertificationCommand data about register fit certification with user id
     * @return Boolean about register success
     */
    @Transactional
    override fun registerFitCertification(registerFitCertificationCommand: RegisterFitCertificationCommand): RegisterFitCertificationResponseDto {
        val fitRecord = readFitRecordPort.findById(registerFitCertificationCommand.fitRecordId)
            .orElseThrow { ResourceNotFoundException("fit record does not exist") }

        if (fitRecord.isDeleted) throw BadRequestException("fit record already deleted")

        if (registerFitCertificationCommand.fitGroupIds.isEmpty()) throw BadRequestException("fit group ids need at least one")

        registerFitCertificationCommand.fitGroupIds.forEach { fitGroupId ->
            readFitCertificationPort.findByUserIdAndFitGroupIdAndCertificationStatus(
                registerFitCertificationCommand.requestUserId,
                fitGroupId,
                CertificationStatus.REQUESTED
            )
                .ifPresent { throw ResourceAlreadyExistException("fit certification already registered and is waiting result") }

            if (readFitCertificationPort.findByFitRecordAndFitGroupIdAndCertificationStatusNot(
                    fitRecord,
                    fitGroupId,
                    CertificationStatus.REJECTED
                ).isNotEmpty()
            ) {
                throw BadRequestException("fit certification already registered on same condition")
            }
        }

        val fitCertifications =
            FitCertification.createFitCertificationsByCommand(fitRecord, registerFitCertificationCommand)

        fitCertifications.forEach {
            val savedFitCertification = registerFitCertificationPort.registerFitCertification(it)
            fitCertificationEventPublishPort.publishFitCertificationRegisterEvent(savedFitCertification.id!!)
        }

        return FitCertificationUseCaseConverter.resultToRegisterResponseDto(true)
    }

    /**
     * Delete fit certification use case,
     * delete fit certification data to persistence
     *
     * @param deleteFitCertificationCommand data about delete fit certification id with user id
     * @return Boolean about delete success
     */
    @Transactional
    override fun deleteFitCertification(deleteFitCertificationCommand: DeleteFitCertificationCommand): DeleteFitCertificationResponseDto {
        val fitCertification = readFitCertificationPort.findById(deleteFitCertificationCommand.fitCertificationId)
            .orElseThrow { ResourceNotFoundException("fit certification does not exist") }

        if (fitCertification.isDeleted)
            throw BadRequestException("fit certification already deleted")

        if (fitCertification.userId != deleteFitCertificationCommand.requestUserId)
            throw BadRequestException("fit certification user not matched with request user")

        if (fitCertification.certificationStatus != CertificationStatus.REQUESTED)
            throw BadRequestException("fit certification already get result. if certification has result can't delete it")

        fitCertification.delete(deleteFitCertificationCommand.requestUserId.toString())
        updateFitCertificationPort.updateFitCertification(fitCertification)

        fitCertificationEventPublishPort.publishFitCertificationDeleteEvent(fitCertification.id!!)

        return FitCertificationUseCaseConverter.resultToDeleteResponseDto(fitCertification.isDeleted)
    }

    @Transactional(readOnly = true)
    override fun getFitCertificationByGroupId(command: FitCertificationFilterByGroupCommand): List<FitCertificationDetailWithVoteResponseDto> {
        val fitCertificationDetailList =
            readFitCertificationPort.findFitCertificationProgressDetailsByGroupId(
                command.fitGroupId,
                command.requestUserId
            )

        val fitCertificationDetailDtoList = fitCertificationDetailList
            .filter { !(command.withOwn != null && command.withOwn == 1 && command.requestUserId == it.certificationRequestUserId) }
            .map {
                val userForRead = getUserForReadPort(it.certificationRequestUserId)

                val fitRecord = readFitRecordPort.findById(it.recordId)
                    .orElseThrow { ResourceNotFoundException("fit record does not exist") }

                FitCertificationDetailWithVoteResponseDto(
                    it.certificationId,
                    it.recordId,
                    it.certificationRequestUserId,
                    userForRead?.nickname,
                    it.isUserVoteDone,
                    it.isUserAgree,
                    it.agreeCount.toInt(),
                    it.disagreeCount.toInt(),
                    it.maxAgreeCount.toInt(),
                    it.fitRecordStartDate,
                    it.fitRecordEndDate,
                    readRecordMultiMediaEndPointPort.findByFitRecordAndOrderByIdAsc(fitRecord).stream()
                        .map { multiMedia -> multiMedia.endPoint }.toList(),
                    it.createdAt.plus(12, ChronoUnit.HOURS)
                )
            }.toList()

        return fitCertificationDetailDtoList
    }

    private fun getUserForReadPort(userId: Int): UserForRead? =
        readUserForReadPort.findByUserId(userId).getOrNull()

    private fun getRecordThumbnailEndPoint(recordId: Long): String? {
        val fitRecord = readFitRecordPort.findById(recordId)
            .orElseThrow { ResourceNotFoundException("fit record does not exist") }

        val multiMediaEndpoints = readRecordMultiMediaEndPointPort.findByFitRecordAndOrderByIdAsc(fitRecord)

        return if (multiMediaEndpoints.isNotEmpty()) multiMediaEndpoints[0].endPoint
        else null
    }

    @Transactional(readOnly = true)
    override fun getFitCertificationProgressByGroupId(command: FitCertificationProgressByGroupIdCommand): FitCertificationProgressesResponse {
        val fitGroupForRead = readFitGroupForReadPort.findByFitGroupId(command.fitGroupId)
            .orElseThrow { ResourceNotFoundException("fit group id does not exist") }

        val fitCertificationProgresses = mutableListOf<FitCertificationProgressesResponseDto>()
        val fitMateForReadList = readFitMateForReadPort.findByFitGroupId(fitGroupForRead.fitGroupId)

        val mondayInstant = getStartOfCurrentWeek()
        fitMateForReadList.forEach {
            val userForRead = getUserForReadPort(it.fitMateUserId)

            val responseDto = FitCertificationProgressesResponseDto(
                it.fitMateUserId,
                userForRead?.nickname,
                readFitCertificationPort.countByUserIdAndFitGroupIdAndCertificationStatusAndDateGreaterThanEqual(
                    it.fitMateUserId,
                    it.fitGroupId,
                    CertificationStatus.CERTIFIED,
                    mondayInstant
                )
            )

            fitCertificationProgresses.add(responseDto)
        }


        return FitCertificationProgressesResponse(
            fitGroupForRead.fitGroupId,
            fitGroupForRead.fitGroupName,
            fitGroupForRead.cycle,
            fitGroupForRead.frequency,
            fitCertificationProgresses
        )
    }

    @Transactional(readOnly = true)
    override fun getFitCertificationDetail(command: FitCertificationDetailCommand): FitCertificationDetailResponseDto {
        val fitCertification = readFitCertificationPort.findById(command.fitCertificationId)
            .orElseThrow { ResourceNotFoundException("fit certification does not exist") }

        return FitCertificationUseCaseConverter.fitCertificationToDetailResponseDto(fitCertification)
    }

    @Transactional(readOnly = true)
    override fun getFitCertificationDetailProgress(command: FitCertificationDetailProgressCommand): FitCertificationDetailProgressResponseDto {
        val fitCertification = readFitCertificationPort.findById(command.fitCertificationId)
            .orElseThrow { ResourceNotFoundException("fit certification does not exist") }

        val agreeCount = readVotePort.countByAgreeAndTargetCategoryAndTargetId(
            GlobalStatus.VOTE_AGREE,
            GlobalStatus.VOTE_TARGET_CATEGORY_CERTIFICATION,
            command.fitCertificationId
        )

        val disAgreeCount = readVotePort.countByAgreeAndTargetCategoryAndTargetId(
            GlobalStatus.VOTE_DISAGREE,
            GlobalStatus.VOTE_TARGET_CATEGORY_CERTIFICATION,
            command.fitCertificationId
        )

        val maxAgreeCount = readFitMateForReadPort.countByFitGroup(fitCertification.fitGroupId)

        return FitCertificationUseCaseConverter.resultToDetailProgressResponseDto(
            fitCertification,
            agreeCount,
            disAgreeCount,
            maxAgreeCount
        )
    }

    fun getStartOfCurrentWeek(): Instant {
        val now = Instant.now()
        val zoneId = ZoneId.systemDefault()
        val zonedDateTime = ZonedDateTime.ofInstant(now, zoneId)
        return zonedDateTime.with(DayOfWeek.MONDAY).toInstant()
    }

    @Transactional
    override fun updateFitCertificationResult(command: UpdateFitCertificationResultCommand): UpdateFitCertificationResultResponseDto {
        val fitCertification = readFitCertificationPort.findById(command.fitCertificationId)
            .orElseThrow { ResourceNotFoundException("fit certification result update. fit certification does not exist") }

        val fitCertificationResult = readFitCertificationResultPort.findByFitCertificationId(command.fitCertificationId)

        fitCertification.updateResult(fitCertificationResult)
        updateFitCertificationPort.updateFitCertification(fitCertification)

        return UpdateFitCertificationResultResponseDto(true)
    }
}