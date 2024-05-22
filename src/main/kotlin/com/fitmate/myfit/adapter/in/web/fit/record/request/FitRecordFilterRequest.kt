package com.fitmate.myfit.adapter.`in`.web.fit.record.request

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

data class FitRecordFilterRequest(
    val userId: Int,
    val recordEndStartDate: Instant =
        LocalDate.now().withDayOfMonth(1)
            .atStartOfDay().toInstant(ZoneOffset.UTC),
    val recordEndEndDate: Instant =
        LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())
            .atStartOfDay().plusHours(23).plusMinutes(59).plusSeconds(59).toInstant(ZoneOffset.UTC)
)
