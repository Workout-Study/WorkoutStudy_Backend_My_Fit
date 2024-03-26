package com.fitmate.myfit.adapter.`in`.web.fit.record.request

import jakarta.validation.constraints.NotEmpty
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

data class FitRecordFilterRequest(
    @field:NotEmpty val userId: String,
    val recordEndStartDate: Instant =
        LocalDate.now().withDayOfMonth(1)
            .atStartOfDay().toInstant(ZoneOffset.UTC),
    val recordEndEndDate: Instant =
        LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())
            .atStartOfDay().plusHours(23).plusMinutes(59).plusSeconds(59).toInstant(ZoneOffset.UTC)
)
