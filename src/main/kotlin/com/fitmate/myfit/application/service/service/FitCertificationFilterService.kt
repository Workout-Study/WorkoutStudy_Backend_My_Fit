package com.fitmate.myfit.application.service.service

import com.fitmate.myfit.adapter.`in`.web.my.fit.response.MyFitGroupIssueSliceFilterResponse
import com.fitmate.myfit.adapter.out.persistence.dto.FitCertificationWithVoteDto
import com.fitmate.myfit.application.port.`in`.my.fit.command.FitCertificationProgressFilterCommand
import com.fitmate.myfit.application.port.`in`.my.fit.command.MyFitGroupIssueSliceFilterCommand
import com.fitmate.myfit.application.port.`in`.my.fit.command.NeedVoteCertificationFilterCommand
import com.fitmate.myfit.application.port.`in`.my.fit.response.FilterCertificationProgressResponseDto
import com.fitmate.myfit.application.port.`in`.my.fit.response.MyFitGroupIssueSliceFilterResponseDto
import com.fitmate.myfit.application.port.`in`.my.fit.response.NeedVoteCertificationFitGroupResponseDto
import com.fitmate.myfit.application.port.`in`.my.fit.response.NeedVoteCertificationResponseDto
import com.fitmate.myfit.application.port.`in`.my.fit.usecase.ReadFitCertificationProgressUseCase
import com.fitmate.myfit.application.port.`in`.my.fit.usecase.ReadFitGroupIssueUseCase
import com.fitmate.myfit.application.port.`in`.my.fit.usecase.ReadNeedVoteCertificationUseCase
import com.fitmate.myfit.application.port.out.certification.ReadFitCertificationPort
import com.fitmate.myfit.application.port.out.fit.group.ReadFitGroupForReadPort
import com.fitmate.myfit.application.port.out.fit.mate.ReadFitMateForReadPort
import com.fitmate.myfit.application.port.out.fit.record.ReadFitRecordPort
import com.fitmate.myfit.application.port.out.fit.record.ReadRecordMultiMediaEndPointPort
import com.fitmate.myfit.application.port.out.user.ReadUserForReadPort
import com.fitmate.myfit.application.port.out.vote.ReadVotePort
import com.fitmate.myfit.common.GlobalStatus
import com.fitmate.myfit.common.exceptions.ResourceNotFoundException
import com.fitmate.myfit.domain.CertificationStatus
import com.fitmate.myfit.domain.FitCertification
import com.fitmate.myfit.domain.FitRecord
import com.fitmate.myfit.domain.UserForRead
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.*
import java.time.temporal.ChronoUnit
import kotlin.jvm.optionals.getOrNull

@Service
class FitCertificationFilterService(
    private val readFitGroupForReadPort: ReadFitGroupForReadPort,
    private val readFitMateForReadPort: ReadFitMateForReadPort,
    private val readFitCertificationPort: ReadFitCertificationPort,
    private val readRecordMultiMediaEndPointPort: ReadRecordMultiMediaEndPointPort,
    private val readFitRecordPort: ReadFitRecordPort,
    private val readUserForReadPort: ReadUserForReadPort,
    private val readVotePort: ReadVotePort
) : ReadFitCertificationProgressUseCase, ReadNeedVoteCertificationUseCase, ReadFitGroupIssueUseCase {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(FitCertificationFilterService::class.java)
    }

    /**
     * Get Filtered Fit certification progress use case
     *
     * @param command filter condition with user id
     * @return content list
     */
    @Transactional(readOnly = true)
    override fun filterFitCertificationProgress(
        command: FitCertificationProgressFilterCommand
    ): List<FilterCertificationProgressResponseDto> {
        val fitMateList = readFitMateForReadPort.findByFitMateUserId(command.userId)

        val responseDto = mutableListOf<FilterCertificationProgressResponseDto>()

        fitMateList.forEach {
            val fitGroupForReadOpt = readFitGroupForReadPort.findByFitGroupId(it.fitGroupId)
            if (fitGroupForReadOpt.isEmpty) {
                logger?.info(
                    "fit group does not exist on filterFitCertificationProgress fit-group-id = {}",
                    it.fitGroupId
                )
                return@forEach
            }

            val fitGroupForRead = fitGroupForReadOpt.get()

            val mondayInstant = getStartOfCurrentWeek()
            val certificationCount =
                readFitCertificationPort.countByUserIdAndFitGroupIdAndCertificationStatusAndDateGreaterThanEqual(
                    command.userId,
                    it.fitGroupId,
                    CertificationStatus.CERTIFIED,
                    mondayInstant
                )

            val responseItem = FilterCertificationProgressResponseDto(
                fitGroupForRead.fitGroupId,
                fitGroupForRead.fitGroupName,
                fitGroupForRead.maxFitMate,
                fitGroupForRead.presentFitMateCount,
                fitGroupForRead.thumbnailEndPoint,
                fitGroupForRead.cycle,
                fitGroupForRead.frequency,
                certificationCount
            )

            responseDto.add(responseItem)
        }

        return responseDto
    }

    fun getStartOfCurrentWeek(): Instant {
        val now = Instant.now()
        val zoneId = ZoneId.systemDefault()
        val zonedDateTime = ZonedDateTime.ofInstant(now, zoneId)
        return zonedDateTime.with(DayOfWeek.MONDAY).toInstant()
    }

    /**
     * Get Filtered need vote certification list use case
     *
     * @param command filter condition with user id
     * @return content list
     */
    @Transactional(readOnly = true)
    override fun filterNeedVoteCertification(command: NeedVoteCertificationFilterCommand): List<NeedVoteCertificationFitGroupResponseDto> {
        val fitMateList = readFitMateForReadPort.findByFitMateUserId(command.userId)

        val responseDto = mutableListOf<NeedVoteCertificationFitGroupResponseDto>()

        fitMateList.forEach {
            val fitGroupForReadOpt = readFitGroupForReadPort.findByFitGroupId(it.fitGroupId)
            if (fitGroupForReadOpt.isEmpty) {
                logger?.info(
                    "fit group does not exist on filterFitCertificationProgress fit-group-id = {}",
                    it.fitGroupId
                )
                return@forEach
            }

            val needVoteCertificationResponseDtoList =
                readFitCertificationPort.findNeedToVoteCertificationByFitGroupIdAndUserId(
                    it.fitGroupId,
                    command.userId
                )

            if (needVoteCertificationResponseDtoList.isNotEmpty()) {
                responseDto.add(
                    NeedVoteCertificationFitGroupResponseDto(
                        it.fitGroupId,
                        fitGroupForReadOpt.get().fitGroupName,
                        needVoteCertificationResponseDtoList.map { e -> toNeedVoteCertificationResponseDto(e) }
                    )
                )
            }
        }

        return responseDto
    }

    private fun toNeedVoteCertificationResponseDto(
        dto: FitCertificationWithVoteDto
    ): NeedVoteCertificationResponseDto {
        val fitRecord = readFitRecordPort.findById(dto.fitRecordId)
            .orElseThrow { ResourceNotFoundException("fit record does not exist") }

        val readUserForReadPort = getUserForReadPort(dto.userId)

        return NeedVoteCertificationResponseDto(
            dto.certificationId,
            dto.fitRecordId,
            dto.userId,
            readUserForReadPort?.nickname,
            dto.agreeCount.toInt(),
            dto.disagreeCount.toInt(),
            dto.maxAgreeCount.toInt(),
            dto.createdAt.plus(12, ChronoUnit.HOURS),
            getFitRecordMultiMediaEndPoints(fitRecord)
        )
    }

    private fun getUserForReadPort(userId: Int): UserForRead? =
        readUserForReadPort.findByUserId(userId).getOrNull()

    private fun getFitRecordMultiMediaEndPoints(fitRecord: FitRecord) =
        readRecordMultiMediaEndPointPort.findByFitRecordAndOrderByIdAsc(fitRecord)
            .map { it.endPoint }

    @Transactional(readOnly = true)
    override fun filterFitGroupIssue(command: MyFitGroupIssueSliceFilterCommand): MyFitGroupIssueSliceFilterResponse {
        val fitGroupIssueStartDate: Instant =
            LocalDate.now().minusWeeks(1)
                .atStartOfDay().toInstant(ZoneOffset.UTC)

        val fitGroupIssueEndDate: Instant =
            LocalDate.now()
                .atTime(23, 59, 59).toInstant(ZoneOffset.UTC)

        val pageable = command.pageable

        val fitGroupIdList = readFitMateForReadPort.findByFitMateUserId(command.userId).map { it.fitGroupId }

        if (fitGroupIdList.isEmpty()) {
            return MyFitGroupIssueSliceFilterResponse(
                pageable.pageNumber,
                pageable.pageSize,
                false,
                emptyList()
            )
        }

        var fitCertificationList =
            readFitCertificationPort.findFitCertificationByFitGroupIssue(
                command,
                fitGroupIdList,
                fitGroupIssueStartDate,
                fitGroupIssueEndDate
            )

        val hasNext: Boolean = fitCertificationList.size > pageable.pageSize
        if (hasNext) fitCertificationList = fitCertificationList.dropLast(1)

        val myFitGroupIssueSliceFilterResponseDtoList = fitCertificationList.map {
            toMyFitGroupIssueSliceFilterResponseDto(it, command)
        }.toList()

        return MyFitGroupIssueSliceFilterResponse(
            pageable.pageNumber,
            pageable.pageSize,
            hasNext,
            myFitGroupIssueSliceFilterResponseDtoList
        )
    }

    private fun toMyFitGroupIssueSliceFilterResponseDto(
        it: FitCertification,
        command: MyFitGroupIssueSliceFilterCommand
    ): MyFitGroupIssueSliceFilterResponseDto {
        val userForRead = getUserForReadPort(it.userId)

        val fitGroup = readFitGroupForReadPort.findByFitGroupId(it.fitGroupId)
            .orElseThrow { ResourceNotFoundException("fit group does not exist") }

        val userVote = readVotePort.findByUserIdAndTargetCategoryAndTargetId(
            command.userId,
            GlobalStatus.VOTE_TARGET_CATEGORY_CERTIFICATION,
            it.id!!
        )

        return MyFitGroupIssueSliceFilterResponseDto(
            it.fitGroupId,
            it.userId,
            userForRead?.nickname,
            getRecordThumbnailEndPoint(it.fitRecord.id!!),
            it.certificationStatus,
            readVotePort.countByAgreeAndTargetCategoryAndTargetId(
                true,
                GlobalStatus.VOTE_TARGET_CATEGORY_CERTIFICATION,
                it.id
            ),
            readVotePort.countByAgreeAndTargetCategoryAndTargetId(
                false,
                GlobalStatus.VOTE_TARGET_CATEGORY_CERTIFICATION,
                it.id
            ),
            readFitMateForReadPort.countByFitGroup(
                it.fitGroupId
            ) - 1,
            userVote.isPresent,
            if (userVote.isPresent) userVote.get().agree else false,
            it.createdAt
        )
    }

    private fun getRecordThumbnailEndPoint(recordId: Long): String? {
        val fitRecord = readFitRecordPort.findById(recordId)
            .orElseThrow { ResourceNotFoundException("fit record does not exist") }

        val multiMediaEndpoints = readRecordMultiMediaEndPointPort.findByFitRecordAndOrderByIdAsc(fitRecord)

        return if (multiMediaEndpoints.isNotEmpty()) multiMediaEndpoints[0].endPoint
        else null
    }
}