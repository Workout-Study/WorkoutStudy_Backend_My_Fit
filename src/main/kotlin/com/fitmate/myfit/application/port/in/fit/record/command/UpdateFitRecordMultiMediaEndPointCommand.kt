package com.fitmate.myfit.application.port.`in`.fit.record.command

import com.fitmate.myfit.common.SelfValidating

data class UpdateFitRecordMultiMediaEndPointCommand(
    val fitRecordId: Long,
    val requestUserId: Int,
    val multiMediaEndPoints: List<String>?
) : SelfValidating<UpdateFitRecordMultiMediaEndPointCommand>() {

    init {
        this.validateSelf()
    }
}
