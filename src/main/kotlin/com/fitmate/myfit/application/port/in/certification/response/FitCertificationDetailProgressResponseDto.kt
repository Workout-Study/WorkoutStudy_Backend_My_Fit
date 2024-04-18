package com.fitmate.myfit.application.port.`in`.certification.response

data class FitCertificationDetailProgressResponseDto(
    val fitCertificationId: Long,
    val agreeCount: Int,
    val disagreeCount: Int,
    val maxAgreeCount: Int
)
