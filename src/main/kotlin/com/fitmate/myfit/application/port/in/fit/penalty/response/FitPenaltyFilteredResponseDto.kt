package com.fitmate.myfit.application.port.`in`.fit.penalty.response

import java.time.Instant

data class FitPenaltyFilteredResponseDto(
    val fitPenaltyId: Long,
    val fitGroupId: Long,
    val userId: String,
    val amount: Int,
    val paid: Boolean,
    val noNeedPay: Boolean,
    val createdAt: Instant
)
