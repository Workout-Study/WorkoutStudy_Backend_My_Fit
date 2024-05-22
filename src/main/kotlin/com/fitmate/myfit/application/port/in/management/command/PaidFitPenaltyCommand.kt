package com.fitmate.myfit.application.port.`in`.management.command

import com.fitmate.myfit.common.SelfValidating

data class PaidFitPenaltyCommand(
    val fitPenaltyId: Long,
    val requestUserId: Int
) : SelfValidating<PaidFitPenaltyCommand>() {

    init {
        this.validateSelf()
    }
}
