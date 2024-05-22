package com.fitmate.myfit.application.port.`in`.vote.command

import com.fitmate.myfit.common.SelfValidating

data class DeleteVoteCommand(
    val requestUserId: Int,
    val targetCategory: Int,
    val targetId: Long
) : SelfValidating<DeleteVoteCommand>() {

    init {
        this.validateSelf()
    }
}
