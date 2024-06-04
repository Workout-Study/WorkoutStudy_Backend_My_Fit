package com.fitmate.myfit.application.port.`in`.user.command

import com.fitmate.myfit.common.SelfValidating
import jakarta.validation.constraints.NotEmpty

data class CreateUserForReadCommand(
    val userId: Int,
    val nickname: String,
    val state: Boolean,
    @field:NotEmpty val eventPublisher: String
) : SelfValidating<CreateUserForReadCommand>() {
}