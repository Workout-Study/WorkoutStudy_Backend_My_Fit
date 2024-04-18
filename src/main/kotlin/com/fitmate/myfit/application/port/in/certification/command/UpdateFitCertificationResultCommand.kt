package com.fitmate.myfit.application.port.`in`.certification.command

import com.fitmate.myfit.common.SelfValidating
import jakarta.validation.constraints.NotEmpty

data class UpdateFitCertificationResultCommand(
    val fitCertificationId: Long,
    @field:NotEmpty val eventPublisher: String
) : SelfValidating<UpdateFitCertificationResultCommand>() {

    init {
        this.validateSelf()
    }
}
