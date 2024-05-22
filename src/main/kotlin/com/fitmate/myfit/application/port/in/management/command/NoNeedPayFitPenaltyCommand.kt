package com.fitmate.myfit.application.port.`in`.management.command

import com.fitmate.myfit.common.SelfValidating

data class NoNeedPayFitPenaltyCommand(
    val fitPenaltyId: Long,
    val requestUserId: Int
) : SelfValidating<NoNeedPayFitPenaltyCommand>() {

    init {
        this.validateSelf()
    }
}
