package com.fitmate.myfit.application.port.`in`.fit.record.command

import com.fitmate.myfit.common.SelfValidating

data class DeleteFitRecordCommand(
    val fitRecordId: Long,
    val requestUserId: Int
) : SelfValidating<DeleteFitRecordCommand>() {

    init {
        this.validateSelf()
    }
}
