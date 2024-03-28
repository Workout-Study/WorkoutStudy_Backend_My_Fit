package com.fitmate.myfit.application.service.converter

import com.fitmate.myfit.application.port.`in`.certification.response.RegisterFitCertificationResponseDto
import com.fitmate.myfit.application.port.`in`.fit.record.response.DeleteFitCertificationResponseDto

class FitCertificationUseCaseConverter private constructor() {

    companion object {
        fun resultToRegisterResponseDto(result: Boolean): RegisterFitCertificationResponseDto {
            return RegisterFitCertificationResponseDto(result)
        }

        fun resultToDeleteResponseDto(result: Boolean): DeleteFitCertificationResponseDto {
            return DeleteFitCertificationResponseDto(result)
        }
    }
}