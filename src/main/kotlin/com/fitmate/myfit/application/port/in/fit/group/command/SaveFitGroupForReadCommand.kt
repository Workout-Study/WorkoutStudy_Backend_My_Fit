package com.fitmate.myfit.application.port.`in`.fit.group.command

import com.fitmate.myfit.common.SelfValidating
import jakarta.validation.constraints.NotEmpty

data class SaveFitGroupForReadCommand(
    val fitGroupId: Long,
    @field:NotEmpty val eventPublisher: String
) : SelfValidating<SaveFitGroupForReadCommand>() {

    init {
        this.validateSelf()
    }
}
