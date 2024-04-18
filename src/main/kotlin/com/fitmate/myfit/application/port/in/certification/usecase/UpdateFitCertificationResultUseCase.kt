package com.fitmate.myfit.application.port.`in`.certification.usecase

import com.fitmate.myfit.application.port.`in`.certification.command.UpdateFitCertificationResultCommand
import com.fitmate.myfit.application.port.`in`.certification.response.UpdateFitCertificationResultResponseDto

interface UpdateFitCertificationResultUseCase {
    fun updateFitCertificationResult(command: UpdateFitCertificationResultCommand): UpdateFitCertificationResultResponseDto
}