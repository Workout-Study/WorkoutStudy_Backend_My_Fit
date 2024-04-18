package com.fitmate.myfit.application.port.`in`.certification.command

import com.fitmate.myfit.common.SelfValidating

data class FitCertificationDetailCommand(
    val fitCertificationId: Long
) : SelfValidating<DeleteFitCertificationCommand>() {

    init {
        this.validateSelf()
    }
}
