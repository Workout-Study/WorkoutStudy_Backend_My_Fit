package com.fitmate.myfit.application.service.converter

import com.fitmate.myfit.application.port.`in`.certification.response.RegisterFitCertificationResponseDto

class FitCertificationUseCaseConverter private constructor() {

    companion object {
        fun resultToRegisterResponseDto(result: Boolean): RegisterFitCertificationResponseDto {
            return RegisterFitCertificationResponseDto(result)
        }
    }
}