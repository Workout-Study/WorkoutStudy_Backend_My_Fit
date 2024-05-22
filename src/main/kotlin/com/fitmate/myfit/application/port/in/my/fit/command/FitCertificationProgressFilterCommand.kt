package com.fitmate.myfit.application.port.`in`.my.fit.command

import com.fitmate.myfit.common.SelfValidating

data class FitCertificationProgressFilterCommand(
    val userId: Int
) : SelfValidating<FitCertificationProgressFilterCommand>() {

    init {
        this.validateSelf()
    }
}
