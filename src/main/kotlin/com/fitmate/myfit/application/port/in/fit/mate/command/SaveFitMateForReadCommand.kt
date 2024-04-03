package com.fitmate.myfit.application.port.`in`.fit.mate.command

import com.fitmate.myfit.common.SelfValidating
import jakarta.validation.constraints.NotEmpty

data class SaveFitMateForReadCommand(
    val fitGroupId: Long,
    @field:NotEmpty val eventPublisher: String
) : SelfValidating<SaveFitMateForReadCommand>() {

    init {
        this.validateSelf()
    }
}
