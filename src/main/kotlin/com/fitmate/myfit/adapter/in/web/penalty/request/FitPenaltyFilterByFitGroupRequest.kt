package com.fitmate.myfit.adapter.`in`.web.penalty.request

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

data class FitPenaltyFilterByFitGroupRequest(
    val fitMateUserId: Int?,
    val startDate: Instant =
        LocalDate.now().withDayOfMonth(1)
            .atStartOfDay().toInstant(ZoneOffset.UTC),
    val endDate: Instant =
        LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())
            .atStartOfDay().plusHours(23).plusMinutes(59).plusSeconds(59).toInstant(ZoneOffset.UTC),
    val onlyPaid: Boolean?,
    val onlyNotPaid: Boolean?,
    val pageNumber: Int = 0,
    val pageSize: Int = 5,
)
