package com.fitmate.myfit.application.port.`in`.fit.off.command

import com.fitmate.myfit.common.SelfValidating
import java.time.Instant

data class RegisterFitOffCommand(
    val requestUserId: Int,
    val fitOffStartDate: Instant,
    val fitOffEndDate: Instant,
    val fitOffReason: String
) : SelfValidating<RegisterFitOffCommand>() {
    init {
        this.validateSelf()
    }
}
