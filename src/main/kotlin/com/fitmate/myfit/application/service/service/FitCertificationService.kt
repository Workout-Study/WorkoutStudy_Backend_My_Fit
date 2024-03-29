package com.fitmate.myfit.application.service.service

import com.fitmate.myfit.application.port.`in`.certification.command.DeleteFitCertificationCommand
import com.fitmate.myfit.application.port.`in`.certification.command.RegisterFitCertificationCommand
import com.fitmate.myfit.application.port.`in`.certification.response.DeleteFitCertificationResponseDto
import com.fitmate.myfit.application.port.`in`.certification.response.RegisterFitCertificationResponseDto
import com.fitmate.myfit.application.port.`in`.certification.usecase.DeleteFitCertificationUseCase
import com.fitmate.myfit.application.port.`in`.certification.usecase.RegisterFitCertificationUseCase
import com.fitmate.myfit.application.port.out.certification.ReadFitCertificationPort
import com.fitmate.myfit.application.port.out.certification.RegisterFitCertificationPort
import com.fitmate.myfit.application.port.out.certification.UpdateFitCertificationPort
import com.fitmate.myfit.application.port.out.fit.record.ReadFitRecordPort
import com.fitmate.myfit.application.service.converter.FitCertificationUseCaseConverter
import com.fitmate.myfit.common.exceptions.BadRequestException
import com.fitmate.myfit.common.exceptions.ResourceAlreadyExistException
import com.fitmate.myfit.common.exceptions.ResourceNotFoundException
import com.fitmate.myfit.domain.CertificationStatus
import com.fitmate.myfit.domain.FitCertification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FitCertificationService(
    private val readFitRecordPort: ReadFitRecordPort,
    private val readFitCertificationPort: ReadFitCertificationPort,
    private val registerFitCertificationPort: RegisterFitCertificationPort,
    private val updateFitCertificationPort: UpdateFitCertificationPort
) : RegisterFitCertificationUseCase, DeleteFitCertificationUseCase {

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

        registerFitCertificationCommand.fitGroupIds.parallelStream().forEach { fitGroupId ->
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
            registerFitCertificationPort.registerFitCertification(it)
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

        return FitCertificationUseCaseConverter.resultToDeleteResponseDto(fitCertification.isDeleted)
    }
}