package com.fitmate.myfit.application.port.`in`.management.command

import com.fitmate.myfit.common.SelfValidating
import jakarta.validation.constraints.NotEmpty

data class PaidFitPenaltyCommand(
    val fitPenaltyId: Long,
    @field:NotEmpty val requestUserId: String
) : SelfValidating<PaidFitPenaltyCommand>() {

    init {
        this.validateSelf()
    }
}
