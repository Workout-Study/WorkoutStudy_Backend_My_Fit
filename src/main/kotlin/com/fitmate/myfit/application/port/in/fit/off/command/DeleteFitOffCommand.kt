package com.fitmate.myfit.application.port.`in`.fit.off.command

import com.fitmate.myfit.common.SelfValidating

data class DeleteFitOffCommand(
    val fitOffId: Long,
    val requestUserId: Int
) : SelfValidating<DeleteFitOffCommand>() {
    init {
        this.validateSelf()
    }
}
