package com.fitmate.myfit.application.port.`in`.fit.off.command

import com.fitmate.myfit.common.SelfValidating

data class GetProceedingFitOffUserCommand(
    val userId: Int
) : SelfValidating<GetProceedingFitOffUserCommand>() {
    init {
        this.validateSelf()
    }
}