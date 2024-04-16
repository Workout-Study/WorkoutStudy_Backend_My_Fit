package com.fitmate.myfit.adapter.`in`.web.certification.response

import com.fitmate.myfit.application.port.`in`.certification.response.FitCertificationDetailWithVoteResponseDto

data class FitCertificationDetailsResponse(
    val fitCertificationDetails: List<FitCertificationDetailWithVoteResponseDto>
)