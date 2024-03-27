package com.fitmate.myfit.application.service.service

import com.fitmate.myfit.application.port.`in`.certification.command.RegisterFitCertificationCommand
import com.fitmate.myfit.application.port.`in`.certification.response.RegisterFitCertificationResponseDto
import com.fitmate.myfit.application.port.`in`.certification.usecase.RegisterFitCertificationUseCase
import com.fitmate.myfit.application.port.out.certification.ReadFitRecordPort
import com.fitmate.myfit.application.port.out.fit.record.ReadFitCertificationPort
import com.fitmate.myfit.application.port.out.fit.record.RegisterFitCertificationPort
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
) : RegisterFitCertificationUseCase {

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

            readFitCertificationPort.findByFitRecordAndFitGroupId(fitRecord, fitGroupId)
                .ifPresent { throw BadRequestException("fit certification already registered on same condition") }
        }

        val fitCertifications =
            FitCertification.createFitCertificationsByCommand(fitRecord, registerFitCertificationCommand)

        fitCertifications.forEach {
            registerFitCertificationPort.registerFitCertification(it)
        }

        return FitCertificationUseCaseConverter.resultToRegisterResponseDto(true)
    }
}