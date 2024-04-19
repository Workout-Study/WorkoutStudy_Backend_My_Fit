package com.fitmate.myfit.application.service.service

import com.fitmate.myfit.adapter.`in`.web.penalty.response.FitPenaltyFilteredResponse
import com.fitmate.myfit.application.port.`in`.fit.penalty.command.FitPenaltyFilterByUserCommand
import com.fitmate.myfit.application.port.`in`.fit.penalty.command.RegisterFitPenaltyCommand
import com.fitmate.myfit.application.port.`in`.fit.penalty.response.FitPenaltyFilteredResponseDto
import com.fitmate.myfit.application.port.`in`.fit.penalty.response.SaveFitPenaltyResponseDto
import com.fitmate.myfit.application.port.`in`.fit.penalty.usecase.ReadFitPenaltyUseCase
import com.fitmate.myfit.application.port.`in`.fit.penalty.usecase.SaveFitPenaltyUseCase
import com.fitmate.myfit.application.port.out.fit.penalty.ReadFitPenaltyApiPort
import com.fitmate.myfit.application.port.out.fit.penalty.ReadFitPenaltyPersistencePort
import com.fitmate.myfit.application.port.out.fit.penalty.SaveFitPenaltyPort
import com.fitmate.myfit.application.service.converter.FitPenaltyUseCaseConverter
import com.fitmate.myfit.domain.FitPenalty
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FitPenaltyService(
    private val readFitPenaltyPersistencePort: ReadFitPenaltyPersistencePort,
    private val saveFitPenaltyPort: SaveFitPenaltyPort,
    private val readFitPenaltyApiPort: ReadFitPenaltyApiPort
) : SaveFitPenaltyUseCase, ReadFitPenaltyUseCase {

    @Transactional
    override fun saveFitPenalty(command: RegisterFitPenaltyCommand): SaveFitPenaltyResponseDto {
        val fitPenaltyDetail = readFitPenaltyApiPort.findByFitPenaltyId(command.fitPenaltyId)

        val fitPenalty = readFitPenaltyPersistencePort.findByFitPenaltyId(command.fitPenaltyId)
            .orElse(FitPenalty.createByFitPenaltyDetail(fitPenaltyDetail, command))

        fitPenalty.updateByFitPenaltyDetail(fitPenaltyDetail, command)
        val savedFitPenalty = saveFitPenaltyPort.saveFitPenalty(fitPenalty)

        return FitPenaltyUseCaseConverter.resultToSaveResponseDto(savedFitPenalty.id != null)
    }

    @Transactional(readOnly = true)
    override fun fitPenaltyFilterByUser(command: FitPenaltyFilterByUserCommand): FitPenaltyFilteredResponse {
        val totalAmount = readFitPenaltyPersistencePort.sumAmountByUserIdAndCondition(command)

        val pageable = PageRequest.of(command.pageNumber, command.pageSize)
        var fitPenalties = readFitPenaltyPersistencePort.findByUserIdAndCondition(command, pageable)

        val hasNext: Boolean = fitPenalties.size > pageable.pageSize
        if (hasNext) fitPenalties = fitPenalties.dropLast(1)

        val fitPenaltyFilteredResponseDtoList = fitPenalties.map {
            FitPenaltyFilteredResponseDto(
                it.fitPenaltyId,
                it.fitGroupId,
                it.userId,
                it.amount,
                it.paid,
                it.noNeedPay,
                it.createdAt
            )
        }.toList()

        return FitPenaltyFilteredResponse(
            pageable.pageNumber,
            pageable.pageSize,
            hasNext,
            totalAmount,
            fitPenaltyFilteredResponseDtoList
        )
    }
}