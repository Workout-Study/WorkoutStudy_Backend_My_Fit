package com.fitmate.myfit.application.port.`in`.vote.command

import com.fitmate.myfit.common.SelfValidating
import jakarta.validation.constraints.NotEmpty

data class UpdateVoteCommand(
    @field:NotEmpty val requestUserId: String,
    var agree: Boolean,
    val targetCategory: Int,
    val targetId: Long
) : SelfValidating<UpdateVoteCommand>() {

    init {
        this.validateSelf()
    }
}
