package com.fitmate.myfit.application.port.`in`.certification.command

import com.fitmate.myfit.common.SelfValidating

data class RegisterFitCertificationCommand(
    val requestUserId: Int,
    val fitRecordId: Long,
    val fitGroupIds: List<Long>
) : SelfValidating<RegisterFitCertificationCommand>() {

    init {
        this.validateSelf()
    }
}
