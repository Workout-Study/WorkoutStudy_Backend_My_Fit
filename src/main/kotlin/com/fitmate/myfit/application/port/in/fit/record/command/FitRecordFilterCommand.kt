package com.fitmate.myfit.application.port.`in`.fit.record.command

import com.fitmate.myfit.common.SelfValidating
import jakarta.validation.constraints.NotEmpty
import java.time.Instant

data class FitRecordFilterCommand(
    @field:NotEmpty val userId: String,
    val recordEndStartDate: Instant,
    val recordEndEndDate: Instant
) : SelfValidating<FitRecordSliceFilterCommand>() {

    init {
        this.validateSelf()
    }
}
