package com.fitmate.myfit.application.port.`in`.certification.command

import com.fitmate.myfit.common.SelfValidating
import jakarta.validation.constraints.NotEmpty

data class DeleteFitCertificationCommand(
    val fitCertificationId: Long,
    @field:NotEmpty val requestUserId: String
) : SelfValidating<DeleteFitCertificationCommand>() {

    init {
        this.validateSelf()
    }
}