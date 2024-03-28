package com.fitmate.myfit.application.port.`in`.fit.record.command

import com.fitmate.myfit.common.SelfValidating
import jakarta.validation.constraints.NotEmpty
import org.springframework.data.domain.Pageable
import java.time.Instant

data class FitRecordSliceFilterCommand(
    @field:NotEmpty val userId: String,
    val recordEndStartDate: Instant? = null,
    val recordEndEndDate: Instant? = null,
    val pageable: Pageable,
) : SelfValidating<FitRecordSliceFilterCommand>() {

    init {
        this.validateSelf()
    }
}