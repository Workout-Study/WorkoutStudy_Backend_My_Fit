package com.fitmate.myfit.application.port.`in`.vote.command

import com.fitmate.myfit.common.SelfValidating
import jakarta.validation.constraints.NotEmpty

data class DeleteVoteCommand(
    @field:NotEmpty val requestUserId: String,
    val targetCategory: Int,
    val targetId: Long
) : SelfValidating<DeleteVoteCommand>() {

    init {
        this.validateSelf()
    }
}
