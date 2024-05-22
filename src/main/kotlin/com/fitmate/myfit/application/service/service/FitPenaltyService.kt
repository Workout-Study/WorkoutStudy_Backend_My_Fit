package com.fitmate.myfit.application.service.service

import com.fitmate.myfit.adapter.`in`.web.penalty.response.FitPenaltyFilteredResponse
import com.fitmate.myfit.application.port.`in`.fit.penalty.command.FitPenaltyFilterByFitGroupCommand
import com.fitmate.myfit.application.port.`in`.fit.penalty.command.FitPenaltyFilterByUserCommand
import com.fitmate.myfit.application.port.`in`.fit.penalty.command.RegisterFitPenaltyCommand
import com.fitmate.myfit.application.port.`in`.fit.penalty.response.FitPenaltyFilteredResponseDto
import com.fitmate.myfit.application.port.`in`.fit.penalty.response.SaveFitPenaltyResponseDto
import com.fitmate.myfit.application.port.`in`.fit.penalty.usecase.ReadFitPenaltyUseCase
import com.fitmate.myfit.application.port.`in`.fit.penalty.usecase.SaveFitPenaltyUseCase
import com.fitmate.myfit.application.port.`in`.management.command.NoNeedPayFitPenaltyCommand
import com.fitmate.myfit.application.port.`in`.management.command.PaidFitPenaltyCommand
import com.fitmate.myfit.application.port.`in`.management.response.NoNeedPayFitPenaltyResponseDto
import com.fitmate.myfit.application.port.`in`.management.response.PaidFitPenaltyResponseDto
import com.fitmate.myfit.application.port.`in`.management.usecase.UpdateFitPenaltyUseCase
import com.fitmate.myfit.application.port.out.fit.group.ReadFitGroupForReadPort
import com.fitmate.myfit.application.port.out.fit.penalty.ReadFitPenaltyApiPort
import com.fitmate.myfit.application.port.out.fit.penalty.ReadFitPenaltyPersistencePort
import com.fitmate.myfit.application.port.out.fit.penalty.SaveFitPenaltyPort
import com.fitmate.myfit.application.service.converter.FitPenaltyUseCaseConverter
import com.fitmate.myfit.common.exceptions.BadRequestException
import com.fitmate.myfit.common.exceptions.ResourceNotFoundException
import com.fitmate.myfit.domain.FitPenalty
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FitPenaltyService(
    private val readFitPenaltyPersistencePort: ReadFitPenaltyPersistencePort,
    private val saveFitPenaltyPort: SaveFitPenaltyPort,
    private val readFitPenaltyApiPort: ReadFitPenaltyApiPort,
    private val readFitGroupForReadPort: ReadFitGroupForReadPort
) : SaveFitPenaltyUseCase, ReadFitPenaltyUseCase, UpdateFitPenaltyUseCase {

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
        val fitPenalties = readFitPenaltyPersistencePort.findByUserIdAndCondition(command, pageable)

        return getFitPenaltyFilteredResponseByFitPenalties(fitPenalties, pageable, totalAmount)
    }

    @Transactional(readOnly = true)
    override fun fitPenaltyFilterByFitGroupId(command: FitPenaltyFilterByFitGroupCommand): FitPenaltyFilteredResponse {
        val totalAmount = readFitPenaltyPersistencePort.sumAmountByFitGroupIdAndCondition(command)

        val pageable = PageRequest.of(command.pageNumber, command.pageSize)
        val fitPenalties = readFitPenaltyPersistencePort.findByFitGroupIdAndCondition(command, pageable)

        return getFitPenaltyFilteredResponseByFitPenalties(fitPenalties, pageable, totalAmount)
    }

    private fun getFitPenaltyFilteredResponseByFitPenalties(
        fitPenalties: List<FitPenalty>,
        pageable: PageRequest,
        totalAmount: Int
    ): FitPenaltyFilteredResponse {
        var fitPenaltiesList = fitPenalties
        val hasNext: Boolean = fitPenaltiesList.size > pageable.pageSize
        if (hasNext) fitPenaltiesList = fitPenaltiesList.dropLast(1)

        val fitPenaltyFilteredResponseDtoList = fitPenaltiesList.map {
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

    @Transactional
    override fun paidFitPenaltyByFitReader(command: PaidFitPenaltyCommand): PaidFitPenaltyResponseDto {
        val fitPenalty = readFitPenaltyPersistencePort.findByFitPenaltyId(command.fitPenaltyId)
            .orElseThrow { ResourceNotFoundException("fit penalty does not exist") }

        if (fitPenalty.isDeleted) throw BadRequestException("fit penalty already deleted")

        val fitGroup = readFitGroupForReadPort.findByFitGroupId(fitPenalty.fitGroupId)
            .orElseThrow { ResourceNotFoundException("fit group does not exist") }

        if (fitGroup.fitLeaderUserId != command.requestUserId) throw BadRequestException("fit penalty paid check only can fit leader. request user is not fit leader")

        fitPenalty.payPenalty(command.requestUserId.toString())
        val savedFitPenalty = saveFitPenaltyPort.saveFitPenalty(fitPenalty)

        return PaidFitPenaltyResponseDto(savedFitPenalty.paid)
    }

    @Transactional
    override fun noNeedPayFitPenaltyByFitReader(command: NoNeedPayFitPenaltyCommand): NoNeedPayFitPenaltyResponseDto {
        val fitPenalty = readFitPenaltyPersistencePort.findByFitPenaltyId(command.fitPenaltyId)
            .orElseThrow { ResourceNotFoundException("fit penalty does not exist") }

        if (fitPenalty.isDeleted) throw BadRequestException("fit penalty already deleted")

        val fitGroup = readFitGroupForReadPort.findByFitGroupId(fitPenalty.fitGroupId)
            .orElseThrow { ResourceNotFoundException("fit group does not exist") }

        if (fitGroup.fitLeaderUserId != command.requestUserId) throw BadRequestException("fit penalty no need pay check only can fit leader. request user is not fit leader")

        fitPenalty.noNeedPayCheck(command.requestUserId.toString())
        val savedFitPenalty = saveFitPenaltyPort.saveFitPenalty(fitPenalty)

        return NoNeedPayFitPenaltyResponseDto(savedFitPenalty.noNeedPay)
    }
}