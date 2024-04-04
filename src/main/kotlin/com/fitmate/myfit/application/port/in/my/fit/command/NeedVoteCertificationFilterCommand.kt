package com.fitmate.myfit.application.port.`in`.my.fit.command

import com.fitmate.myfit.common.SelfValidating
import jakarta.validation.constraints.NotEmpty

data class NeedVoteCertificationFilterCommand(
    @field:NotEmpty val userId: String
) : SelfValidating<NeedVoteCertificationFilterCommand>() {

    init {
        this.validateSelf()
    }
}