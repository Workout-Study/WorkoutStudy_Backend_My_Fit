package com.fitmate.myfit.application.port.`in`.fit.record.command

import com.fitmate.myfit.common.SelfValidating
import java.time.Instant

data class FitRecordFilterCommand(
    val userId: Int,
    val recordEndStartDate: Instant,
    val recordEndEndDate: Instant
) : SelfValidating<FitRecordFilterCommand>() {

    init {
        this.validateSelf()
    }
}
