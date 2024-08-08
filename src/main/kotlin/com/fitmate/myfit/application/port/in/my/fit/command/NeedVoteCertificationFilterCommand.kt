package com.fitmate.myfit.application.port.`in`.my.fit.command

import com.fitmate.myfit.common.SelfValidating

data class NeedVoteCertificationFilterCommand(
    val userId: Int,
    val fitGroupId: Long?
) : SelfValidating<NeedVoteCertificationFilterCommand>() {

    init {
        this.validateSelf()
    }
}