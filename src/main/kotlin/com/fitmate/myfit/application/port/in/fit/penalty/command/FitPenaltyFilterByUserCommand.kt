package com.fitmate.myfit.application.port.`in`.fit.penalty.command

import com.fitmate.myfit.common.SelfValidating
import java.time.Instant

data class FitPenaltyFilterByUserCommand(
    val userId: Int,
    val fitGroupId: Long?,
    val startDate: Instant,
    val endDate: Instant,
    val onlyPaid: Boolean?,
    val onlyNotPaid: Boolean?,
    val pageNumber: Int = 0,
    val pageSize: Int = 5,
) : SelfValidating<FitPenaltyFilterByUserCommand>() {

    init {
        this.validateSelf()
    }
}
