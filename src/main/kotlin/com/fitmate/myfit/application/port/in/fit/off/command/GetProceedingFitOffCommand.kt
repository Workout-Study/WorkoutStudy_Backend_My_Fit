package com.fitmate.myfit.application.port.`in`.fit.off.command

import com.fitmate.myfit.common.SelfValidating

data class GetProceedingFitOffCommand(
    val fitGroupId: Long
) : SelfValidating<GetProceedingFitOffCommand>() {
    init {
        this.validateSelf()
    }
}