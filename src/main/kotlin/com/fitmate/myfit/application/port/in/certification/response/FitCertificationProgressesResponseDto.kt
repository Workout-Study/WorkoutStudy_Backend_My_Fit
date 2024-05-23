package com.fitmate.myfit.application.port.`in`.certification.response

data class FitCertificationProgressesResponseDto(
    val fitMateUserId: Int,
    val fitMateUserNickname: String?,
    val certificationCount: Int
)
