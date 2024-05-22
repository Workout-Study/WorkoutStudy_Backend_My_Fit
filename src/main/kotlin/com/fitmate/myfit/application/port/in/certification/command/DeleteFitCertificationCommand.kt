package com.fitmate.myfit.application.port.`in`.certification.command

import com.fitmate.myfit.common.SelfValidating

data class DeleteFitCertificationCommand(
    val fitCertificationId: Long,
    val requestUserId: Int
) : SelfValidating<DeleteFitCertificationCommand>() {

    init {
        this.validateSelf()
    }
}