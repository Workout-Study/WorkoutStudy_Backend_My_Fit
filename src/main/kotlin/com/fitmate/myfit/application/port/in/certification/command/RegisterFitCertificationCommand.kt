package com.fitmate.myfit.application.port.`in`.certification.command

import com.fitmate.myfit.application.port.`in`.fit.record.command.RegisterFitRecordCommand
import com.fitmate.myfit.common.SelfValidating
import jakarta.validation.constraints.NotEmpty

data class RegisterFitCertificationCommand(
    @field:NotEmpty val requestUserId: String,
    val fitRecordId: Long,
    val fitGroupIds: List<Long>
) : SelfValidating<RegisterFitRecordCommand>() {

    init {
        this.validateSelf()
    }
}
