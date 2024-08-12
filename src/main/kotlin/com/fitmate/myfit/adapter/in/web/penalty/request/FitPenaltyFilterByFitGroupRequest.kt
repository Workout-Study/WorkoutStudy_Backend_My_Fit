package com.fitmate.myfit.adapter.`in`.web.penalty.request

import com.fitmate.myfit.adapter.out.api.DateParseUtils
import org.springframework.util.StringUtils
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

data class FitPenaltyFilterByFitGroupRequest(
    val fitMateUserId: Int?,
    private val startDate: String?,
    private val endDate: String?,
    val onlyPaid: Boolean?,
    val onlyNotPaid: Boolean?,
    val pageNumber: Int = 0,
    val pageSize: Int = 5,
) {
    val startDateInstant: Instant =
        if (StringUtils.hasText(startDate)) DateParseUtils.stringToInstant(startDate!!)
        else LocalDate.now().withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC)

    val endDateInstant: Instant =
        if (StringUtils.hasText(endDate)) DateParseUtils.stringToInstant(endDate!!)
        else LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())
            .atStartOfDay().plusHours(23).plusMinutes(59).plusSeconds(59).toInstant(ZoneOffset.UTC)
}
