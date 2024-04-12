package com.fitmate.myfit.application.service.service

import com.fitmate.myfit.adapter.out.persistence.dto.FitCertificationWithVoteDto
import com.fitmate.myfit.application.port.`in`.my.fit.command.FitCertificationProgressFilterCommand
import com.fitmate.myfit.application.port.`in`.my.fit.command.NeedVoteCertificationFilterCommand
import com.fitmate.myfit.application.port.`in`.my.fit.response.FilterCertificationProgressResponseDto
import com.fitmate.myfit.application.port.`in`.my.fit.response.NeedVoteCertificationFitGroupResponseDto
import com.fitmate.myfit.application.port.`in`.my.fit.response.NeedVoteCertificationResponseDto
import com.fitmate.myfit.application.port.`in`.my.fit.usecase.ReadFitCertificationProgressUseCase
import com.fitmate.myfit.application.port.`in`.my.fit.usecase.ReadNeedVoteCertificationUseCase
import com.fitmate.myfit.application.port.out.certification.ReadFitCertificationPort
import com.fitmate.myfit.application.port.out.fit.group.ReadFitGroupForReadPort
import com.fitmate.myfit.application.port.out.fit.mate.ReadFitMateForReadPort
import com.fitmate.myfit.application.port.out.fit.record.ReadFitRecordPort
import com.fitmate.myfit.application.port.out.fit.record.ReadRecordMultiMediaEndPointPort
import com.fitmate.myfit.common.exceptions.ResourceNotFoundException
import com.fitmate.myfit.domain.CertificationStatus
import com.fitmate.myfit.domain.FitRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

@Service
class FitCertificationFilterService(
    private val readFitGroupForReadPort: ReadFitGroupForReadPort,
    private val readFitMateForReadPort: ReadFitMateForReadPort,
    private val readFitCertificationPort: ReadFitCertificationPort,
    private val readRecordMultiMediaEndPointPort: ReadRecordMultiMediaEndPointPort,
    private val readFitRecordPort: ReadFitRecordPort
) : ReadFitCertificationProgressUseCase, ReadNeedVoteCertificationUseCase {

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

        return NeedVoteCertificationResponseDto(
            dto.certificationId,
            dto.fitRecordId,
            dto.userId,
            dto.agreeCount.toInt(),
            dto.disagreeCount.toInt(),
            dto.maxAgreeCount.toInt(),
            dto.createdAt.plus(12, ChronoUnit.HOURS),
            getFitRecordMultiMediaEndPoints(fitRecord)
        )
    }

    private fun getFitRecordMultiMediaEndPoints(fitRecord: FitRecord) =
        readRecordMultiMediaEndPointPort.findByFitRecordAndOrderByIdAsc(fitRecord)
            .map { it.endPoint }
}