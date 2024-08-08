package com.fitmate.myfit.application.service.service

import com.fitmate.myfit.adapter.`in`.web.fit.off.response.FitOffDetail
import com.fitmate.myfit.adapter.`in`.web.fit.off.response.ProceedingFitOffResponse
import com.fitmate.myfit.application.port.`in`.fit.off.command.DeleteFitOffCommand
import com.fitmate.myfit.application.port.`in`.fit.off.command.GetProceedingFitOffCommand
import com.fitmate.myfit.application.port.`in`.fit.off.command.RegisterFitOffCommand
import com.fitmate.myfit.application.port.`in`.fit.off.command.UpdateFitOffCommand
import com.fitmate.myfit.application.port.`in`.fit.off.response.DeleteFitOffResponseDto
import com.fitmate.myfit.application.port.`in`.fit.off.response.RegisterFitOffResponseDto
import com.fitmate.myfit.application.port.`in`.fit.off.response.UpdateFitOffResponseDto
import com.fitmate.myfit.application.port.`in`.fit.off.usecase.DeleteFitOffUseCase
import com.fitmate.myfit.application.port.`in`.fit.off.usecase.ReadFitOffUseCase
import com.fitmate.myfit.application.port.`in`.fit.off.usecase.RegisterFitOffUseCase
import com.fitmate.myfit.application.port.`in`.fit.off.usecase.UpdateFitOffUseCase
import com.fitmate.myfit.application.port.out.fit.group.ReadFitGroupForReadPort
import com.fitmate.myfit.application.port.out.fit.mate.ReadFitMateForReadPort
import com.fitmate.myfit.application.port.out.fit.off.ReadFitOffPort
import com.fitmate.myfit.application.port.out.fit.off.RegisterFitOffPort
import com.fitmate.myfit.application.port.out.fit.off.UpdateFitOffPort
import com.fitmate.myfit.application.service.converter.FitOffUseCaseConverter
import com.fitmate.myfit.common.exceptions.BadRequestException
import com.fitmate.myfit.common.exceptions.ResourceNotFoundException
import com.fitmate.myfit.domain.FitOff
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class FitOffService(
    private val readFitOffPort: ReadFitOffPort,
    private val registerFitOffPort: RegisterFitOffPort,
    private val updateFitOffPort: UpdateFitOffPort,
    private val readFitGroupForReadPort: ReadFitGroupForReadPort,
    private val readFitMateForReadPort: ReadFitMateForReadPort
) : RegisterFitOffUseCase, DeleteFitOffUseCase, UpdateFitOffUseCase, ReadFitOffUseCase {

    /**
     * Register fit off use case,
     * register fit off data to persistence
     *
     * @param registerFitOffCommand data about register fit off with user id
     * @return Boolean about register success
     */
    @Transactional
    override fun registerFitOff(registerFitOffCommand: RegisterFitOffCommand): RegisterFitOffResponseDto {
        val fitOffAlreadyExist = readFitOffPort.countByUserIdAndFitOffDate(
            registerFitOffCommand.requestUserId,
            registerFitOffCommand.fitOffStartDate,
            registerFitOffCommand.fitOffEndDate
        ) > 0

        if (fitOffAlreadyExist) throw BadRequestException("user fit off already exist on request fit off date.")

        if (registerFitOffCommand.fitOffEndDate.isBefore(registerFitOffCommand.fitOffStartDate))
            throw BadRequestException("fit off end date is must after fit off start date")

        val fitOff = FitOff.createByCommand(registerFitOffCommand)

        val savedFitOff = registerFitOffPort.registerFitOff(fitOff)

        return FitOffUseCaseConverter.fitOffToRegisterResponseDto(savedFitOff)
    }

    /**
     * Delete fit off use case,
     * delete fit off data to persistence
     *
     * @param deleteFitOffCommand data about delete fit off with user id
     * @return Boolean about delete success
     */
    @Transactional
    override fun deleteFitOff(deleteFitOffCommand: DeleteFitOffCommand): DeleteFitOffResponseDto {
        val fitOff = readFitOffPort.findById(deleteFitOffCommand.fitOffId)
            .orElseThrow { ResourceNotFoundException("fit off does not exist") }

        if (fitOff.userId != deleteFitOffCommand.requestUserId)
            throw BadRequestException("fit off only can delete by own user. user does not match")

        if (Instant.now().isAfter(fitOff.fitOffEndDate)) throw BadRequestException("fit off end date already passed")

        fitOff.delete(deleteFitOffCommand.requestUserId.toString())

        updateFitOffPort.updateFitOff(fitOff)

        return FitOffUseCaseConverter.resultToDeleteResponseDto(fitOff.isDeleted)
    }

    /**
     * Update fit off use case,
     * update fit off data to persistence
     *
     * @param updateFitOffCommand data about update fit off with user id
     * @return Boolean about update success
     */
    @Transactional
    override fun updateFitOff(updateFitOffCommand: UpdateFitOffCommand): UpdateFitOffResponseDto {
        val fitOff = readFitOffPort.findById(updateFitOffCommand.fitOffId)
            .orElseThrow { ResourceNotFoundException("fit off does not exist") }

        if (fitOff.userId != updateFitOffCommand.requestUserId)
            throw BadRequestException("fit off only can update by own user. user does not match")

        if (fitOff.fitOffStartDate != updateFitOffCommand.fitOffStartDate && Instant.now()
                .isAfter(updateFitOffCommand.fitOffStartDate)
        )
            throw BadRequestException("fit off start date only can same as before update or after then now.")

        if (updateFitOffCommand.fitOffEndDate.isBefore(updateFitOffCommand.fitOffStartDate))
            throw BadRequestException("fit off end date is must after fit off start date")

        if (Instant.now().isAfter(fitOff.fitOffEndDate)) throw BadRequestException("fit off end date already passed")

        if (Instant.now()
                .isAfter(updateFitOffCommand.fitOffEndDate)
        ) throw BadRequestException("update fit off end date must be after then now")

        fitOff.updateByCommand(updateFitOffCommand)

        updateFitOffPort.updateFitOff(fitOff)

        return FitOffUseCaseConverter.resultToUpdateResponseDto(true)
    }

    @Transactional(readOnly = true)
    override fun getProceedingFitOffByGroupId(command: GetProceedingFitOffCommand): ProceedingFitOffResponse {
        val fitGroup = readFitGroupForReadPort.findByFitGroupId(command.fitGroupId)
            .orElseThrow { ResourceNotFoundException("fit group does not exist") }

        val fitMateUserIdList = readFitMateForReadPort.findByFitGroupId(fitGroup.id!!)
            .map { it.fitMateUserId }.toList()

        val fitOffDetailList = readFitOffPort.findProceedingFitOffByUserIds(fitMateUserIdList)
            .map { FitOffDetail(it.id!!, it.userId, it.fitOffStartDate, it.fitOffEndDate, it.fitOffReason) }
            .toList()

        return ProceedingFitOffResponse(fitOffDetailList)
    }
}