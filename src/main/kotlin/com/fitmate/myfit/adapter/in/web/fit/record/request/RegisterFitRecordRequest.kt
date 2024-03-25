package com.fitmate.myfit.adapter.`in`.web.fit.record.request

import jakarta.validation.constraints.NotEmpty
import java.time.Instant

data class RegisterFitRecordRequest(
    @field:NotEmpty val requestUserId: String,
    val recordStartDate: Instant,
    val recordEndDate: Instant,
    val multiMediaEndPoints: List<String>?
)
