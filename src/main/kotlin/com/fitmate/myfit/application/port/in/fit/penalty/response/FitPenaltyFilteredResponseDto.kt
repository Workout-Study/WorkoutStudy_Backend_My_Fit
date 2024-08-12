package com.fitmate.myfit.application.port.`in`.fit.penalty.response

import com.fitmate.myfit.adapter.out.api.DateParseUtils
import java.time.Instant

data class FitPenaltyFilteredResponseDto(
    val fitPenaltyId: Long,
    val fitGroupId: Long,
    val userId: Int,
    val userNickname: String?,
    val amount: Int,
    val paid: Boolean,
    val noNeedPay: Boolean,
    private val createdAtInstant: Instant
) {
    val createdAt = DateParseUtils.instantToString(createdAtInstant)
}
