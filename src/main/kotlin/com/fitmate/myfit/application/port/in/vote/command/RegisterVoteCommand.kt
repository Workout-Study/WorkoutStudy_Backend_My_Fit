package com.fitmate.myfit.application.port.`in`.vote.command

import com.fitmate.myfit.common.SelfValidating

data class RegisterVoteCommand(
    val requestUserId: Int,
    var agree: Boolean,
    val targetCategory: Int,
    val targetId: Long
) : SelfValidating<RegisterVoteCommand>() {

    init {
        this.validateSelf()
    }
}
