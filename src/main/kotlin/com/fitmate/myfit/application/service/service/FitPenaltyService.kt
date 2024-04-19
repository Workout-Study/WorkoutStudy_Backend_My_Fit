package com.fitmate.myfit.application.service.service

import com.fitmate.myfit.application.port.`in`.fit.penalty.command.RegisterFitPenaltyCommand
import com.fitmate.myfit.application.port.`in`.fit.penalty.response.SaveFitPenaltyResponseDto
import com.fitmate.myfit.application.port.`in`.fit.penalty.usecase.SaveFitPenaltyUseCase
import com.fitmate.myfit.application.port.out.fit.penalty.ReadFitPenaltyApiPort
import com.fitmate.myfit.application.port.out.fit.penalty.ReadFitPenaltyPersistencePort
import com.fitmate.myfit.application.port.out.fit.penalty.SaveFitPenaltyPort
import com.fitmate.myfit.application.service.converter.FitPenaltyUseCaseConverter
import com.fitmate.myfit.domain.FitPenalty
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FitPenaltyService(
    private val readFitPenaltyPersistencePort: ReadFitPenaltyPersistencePort,
    private val saveFitPenaltyPort: SaveFitPenaltyPort,
    private val readFitPenaltyApiPort: ReadFitPenaltyApiPort
) : SaveFitPenaltyUseCase {

    @Transactional
    override fun saveFitPenalty(command: RegisterFitPenaltyCommand): SaveFitPenaltyResponseDto {
        val fitPenaltyDetail = readFitPenaltyApiPort.findByFitPenaltyId(command.fitPenaltyId)

        val fitPenalty = readFitPenaltyPersistencePort.findByFitPenaltyId(command.fitPenaltyId)
            .orElse(FitPenalty.createByFitPenaltyDetail(fitPenaltyDetail, command))

        fitPenalty.updateByFitPenaltyDetail(fitPenaltyDetail, command)
        val savedFitPenalty = saveFitPenaltyPort.saveFitPenalty(fitPenalty)

        return FitPenaltyUseCaseConverter.resultToSaveResponseDto(savedFitPenalty.id != null)
    }
}