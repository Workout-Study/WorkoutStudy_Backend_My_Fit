package com.fitmate.myfit.application.port.`in`.fit.record.command

import com.fitmate.myfit.common.SelfValidating
import java.time.Instant

data class RegisterFitRecordCommand(
    val requestUserId: Int,
    val recordStartDate: Instant,
    val recordEndDate: Instant,
    val multiMediaEndPoints: List<String>?
) : SelfValidating<RegisterFitRecordCommand>() {

    init {
        this.validateSelf()
    }
}