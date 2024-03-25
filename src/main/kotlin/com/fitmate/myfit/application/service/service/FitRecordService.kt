package com.fitmate.myfit.application.service.service

import com.fitmate.myfit.application.port.`in`.command.RegisterFitRecordCommand
import com.fitmate.myfit.application.port.`in`.response.RegisterFitRecordResponseDto
import com.fitmate.myfit.application.port.`in`.usecase.RegisterFitRecordUseCase
import com.fitmate.myfit.application.port.out.ReadFitRecordPort
import com.fitmate.myfit.application.port.out.RegisterFitRecordPort
import com.fitmate.myfit.application.port.out.RegisterRecordMultiMediaEndPoint
import com.fitmate.myfit.application.service.converter.FollowUseCaseConverter
import com.fitmate.myfit.domain.FitRecord
import com.fitmate.myfit.domain.FitRecordMultiMediaEndPoint
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FitRecordService(
    private val readFitRecordPort: ReadFitRecordPort,
    private val registerFitRecordPort: RegisterFitRecordPort,
    private val registerRecordMultiMediaEndPoint: RegisterRecordMultiMediaEndPoint
) : RegisterFitRecordUseCase {

    /**
     * Register fit record use case,
     * register fit record data to persistence
     *
     * @param registerFitRecordCommand data about register fit record with user id
     * @return Boolean about register success
     */
    @Transactional
    override fun registerFitRecord(registerFitRecordCommand: RegisterFitRecordCommand): RegisterFitRecordResponseDto {
        val fitRecord = FitRecord.createByCommand(registerFitRecordCommand)

        val savedFitRecord = registerFitRecordPort.registerFitRecord(fitRecord)

        registerFitRecordCommand.multiMediaEndPoints?.let {
            val fitRecordMultiMediaEndPoints = registerFitRecordCommand.multiMediaEndPoints.map {
                FitRecordMultiMediaEndPoint.createFitRecord(savedFitRecord, it)
            }

            registerRecordMultiMediaEndPoint.saveAll(fitRecordMultiMediaEndPoints)
        }

        return FollowUseCaseConverter.fitRecordToRegisterResponseDto(savedFitRecord)
    }
}