package com.fitmate.myfit.adapter.`in`.web.fit.record.request

import jakarta.validation.constraints.NotEmpty
import java.time.Instant

data class FitRecordSliceFilterRequest(
    @field:NotEmpty val userId: String,
    val recordEndStartDate: Instant? = null,
    val recordEndEndDate: Instant? = null,
    val pageNumber: Int = 0,
    val pageSize: Int = 5,
)
