package com.fitmate.myfit.application.port.`in`.user.command

import com.fitmate.myfit.common.SelfValidating
import jakarta.validation.constraints.NotEmpty

data class SaveUserForReadCommand(
    val userId: Int,
    @field:NotEmpty val eventPublisher: String
) : SelfValidating<SaveUserForReadCommand>() {

    init {
        this.validateSelf()
    }
}