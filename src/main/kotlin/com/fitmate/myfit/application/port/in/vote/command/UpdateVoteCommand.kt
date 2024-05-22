package com.fitmate.myfit.application.port.`in`.vote.command

import com.fitmate.myfit.common.SelfValidating

data class UpdateVoteCommand(
    val requestUserId: Int,
    var agree: Boolean,
    val targetCategory: Int,
    val targetId: Long
) : SelfValidating<UpdateVoteCommand>() {

    init {
        this.validateSelf()
    }
}
