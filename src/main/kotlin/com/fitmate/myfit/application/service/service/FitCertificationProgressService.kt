package com.fitmate.myfit.application.service.service

import com.fitmate.myfit.application.port.`in`.my.fit.command.FitCertificationProgressFilterCommand
import com.fitmate.myfit.application.port.`in`.my.fit.response.FilterCertificationProgressResponseDto
import com.fitmate.myfit.application.port.`in`.my.fit.usecase.ReadFitCertificationProgressUseCase
import com.fitmate.myfit.application.port.out.certification.ReadFitCertificationPort
import com.fitmate.myfit.application.port.out.fit.group.ReadFitGroupForReadPort
import com.fitmate.myfit.application.port.out.fit.mate.ReadFitMateForReadPort
import com.fitmate.myfit.domain.CertificationStatus
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@Service
class FitCertificationProgressService(
    private val readFitGroupForReadPort: ReadFitGroupForReadPort,
    private val readFitMateForReadPort: ReadFitMateForReadPort,
    private val readFitCertificationPort: ReadFitCertificationPort
) : ReadFitCertificationProgressUseCase {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(FitCertificationProgressService::class.java)
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
}