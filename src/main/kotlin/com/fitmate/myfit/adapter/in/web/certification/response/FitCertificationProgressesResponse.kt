package com.fitmate.myfit.adapter.`in`.web.certification.response

import com.fitmate.myfit.application.port.`in`.certification.response.FitCertificationProgressesResponseDto

data class FitCertificationProgressesResponse(
    val fitGroupId: Long,
    val fitGroupName: String,
    val cycle: Int,
    val frequency: Int,
    val fitCertificationProgresses: List<FitCertificationProgressesResponseDto>
)
