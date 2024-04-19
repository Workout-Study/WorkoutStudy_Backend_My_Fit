package com.fitmate.myfit.application.port.`in`.fit.penalty.command

import com.fitmate.myfit.common.SelfValidating

data class RegisterFitPenaltyCommand(
    val fitPenaltyId: Long,
    val eventPublisher: String
) : SelfValidating<RegisterFitPenaltyCommand>() {
    init {
        this.validateSelf()
    }
}
