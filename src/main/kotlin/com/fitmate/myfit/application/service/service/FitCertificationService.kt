package com.fitmate.myfit.application.service.service

import com.fitmate.myfit.adapter.`in`.web.certification.response.FitCertificationProgressesResponse
import com.fitmate.myfit.application.port.`in`.certification.command.DeleteFitCertificationCommand
import com.fitmate.myfit.application.port.`in`.certification.command.FitCertificationDetailCommand
import com.fitmate.myfit.application.port.`in`.certification.command.FitCertificationProgressByGroupIdCommand
import com.fitmate.myfit.application.port.`in`.certification.command.RegisterFitCertificationCommand
import com.fitmate.myfit.application.port.`in`.certification.response.*
import com.fitmate.myfit.application.port.`in`.certification.usecase.DeleteFitCertificationUseCase
import com.fitmate.myfit.application.port.`in`.certification.usecase.ReadFitCertificationUseCase
import com.fitmate.myfit.application.port.`in`.certification.usecase.RegisterFitCertificationUseCase
import com.fitmate.myfit.application.port.`in`.my.fit.command.FitCertificationFilterByGroupCommand
import com.fitmate.myfit.application.port.out.certification.FitCertificationEventPublishPort
import com.fitmate.myfit.application.port.out.certification.ReadFitCertificationPort
import com.fitmate.myfit.application.port.out.certification.RegisterFitCertificationPort
import com.fitmate.myfit.application.port.out.certification.UpdateFitCertificationPort
import com.fitmate.myfit.application.port.out.fit.group.ReadFitGroupForReadPort
import com.fitmate.myfit.application.port.out.fit.mate.ReadFitMateForReadPort
import com.fitmate.myfit.application.port.out.fit.record.ReadFitRecordPort
import com.fitmate.myfit.application.service.converter.FitCertificationUseCaseConverter
import com.fitmate.myfit.common.exceptions.BadRequestException
import com.fitmate.myfit.common.exceptions.ResourceAlreadyExistException
import com.fitmate.myfit.common.exceptions.ResourceNotFoundException
import com.fitmate.myfit.domain.CertificationStatus
import com.fitmate.myfit.domain.FitCertification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@Service
class FitCertificationService(
    private val readFitRecordPort: ReadFitRecordPort,
    private val readFitCertificationPort: ReadFitCertificationPort,
    private val registerFitCertificationPort: RegisterFitCertificationPort,
    private val updateFitCertificationPort: UpdateFitCertificationPort,
    private val readFitGroupForReadPort: ReadFitGroupForReadPort,
    private val readFitMateForReadPort: ReadFitMateForReadPort,
    private val fitCertificationEventPublishPort: FitCertificationEventPublishPort
) : RegisterFitCertificationUseCase, DeleteFitCertificationUseCase, ReadFitCertificationUseCase {

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

        fitCertification.delete(deleteFitCertificationCommand.requestUserId)
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

        return fitCertificationDetailList
    }

    @Transactional(readOnly = true)
    override fun getFitCertificationProgressByGroupId(command: FitCertificationProgressByGroupIdCommand): FitCertificationProgressesResponse {
        val fitGroupForRead = readFitGroupForReadPort.findByFitGroupId(command.fitGroupId)
            .orElseThrow { ResourceNotFoundException("fit group id does not exist") }

        val fitCertificationProgresses = mutableListOf<FitCertificationProgressesResponseDto>()
        val fitMateForReadList = readFitMateForReadPort.findByFitGroupId(fitGroupForRead.fitGroupId)

        val mondayInstant = getStartOfCurrentWeek()
        fitMateForReadList.forEach {
            val responseDto = FitCertificationProgressesResponseDto(
                it.fitMateUserId,
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

    fun getStartOfCurrentWeek(): Instant {
        val now = Instant.now()
        val zoneId = ZoneId.systemDefault()
        val zonedDateTime = ZonedDateTime.ofInstant(now, zoneId)
        return zonedDateTime.with(DayOfWeek.MONDAY).toInstant()
    }
}