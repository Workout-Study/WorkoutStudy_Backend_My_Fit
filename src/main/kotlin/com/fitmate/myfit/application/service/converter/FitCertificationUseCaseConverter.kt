package com.fitmate.myfit.application.service.converter

import com.fitmate.myfit.application.port.`in`.certification.response.DeleteFitCertificationResponseDto
import com.fitmate.myfit.application.port.`in`.certification.response.FitCertificationDetailProgressResponseDto
import com.fitmate.myfit.application.port.`in`.certification.response.FitCertificationDetailResponseDto
import com.fitmate.myfit.application.port.`in`.certification.response.RegisterFitCertificationResponseDto
import com.fitmate.myfit.domain.FitCertification
import java.time.temporal.ChronoUnit

class FitCertificationUseCaseConverter private constructor() {

    companion object {
        fun resultToRegisterResponseDto(result: Boolean): RegisterFitCertificationResponseDto {
            return RegisterFitCertificationResponseDto(result)
        }

        fun resultToDeleteResponseDto(result: Boolean): DeleteFitCertificationResponseDto {
            return DeleteFitCertificationResponseDto(result)
        }

        fun fitCertificationToDetailResponseDto(fitCertification: FitCertification): FitCertificationDetailResponseDto =
            FitCertificationDetailResponseDto(
                fitCertification.id!!,
                fitCertification.certificationStatus,
                fitCertification.state,
                fitCertification.createdAt,
                fitCertification.createdAt.plus(12, ChronoUnit.HOURS)
            )

        fun resultToDetailProgressResponseDto(
            fitCertification: FitCertification,
            agreeCount: Int,
            disAgreeCount: Int,
            maxAgreeCount: Int
        ): FitCertificationDetailProgressResponseDto =
            FitCertificationDetailProgressResponseDto(
                fitCertification.id!!,
                agreeCount,
                disAgreeCount,
                maxAgreeCount
            )

    }
}