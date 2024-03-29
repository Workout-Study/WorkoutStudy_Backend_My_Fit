package com.fitmate.myfit.application.port.`in`.fit.record.command

import com.fitmate.myfit.common.SelfValidating
import jakarta.validation.constraints.NotEmpty

data class DeleteFitRecordCommand(
    val fitRecordId: Long,
    @field:NotEmpty val requestUserId: String
) : SelfValidating<DeleteFitRecordCommand>() {

    init {
        this.validateSelf()
    }
}
