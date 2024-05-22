package com.fitmate.myfit.application.service.dto

import java.time.Instant

data class FitPenaltyDetailResponseDto(
    val fitPenaltyId: Long,
    val fitGroupId: Long,
    val userId: Int,
    val amount: Int,
    val createdAt: Instant
)
