package com.fitmate.myfit.adapter.`in`.web.fit.record.request

import com.fitmate.myfit.adapter.out.api.DateParseUtils
import org.springframework.util.StringUtils
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

data class FitRecordFilterRequest(
    val userId: Int,
    private val recordEndStartDate: String? = null,
    private val recordEndEndDate: String? = null
) {
    val recordEndStartDateInstant: Instant =
        if (StringUtils.hasText(recordEndStartDate)) DateParseUtils.stringToInstant(recordEndStartDate!!)
        else LocalDate.now().withDayOfMonth(1).atStartOfDay().toInstant(ZoneOffset.UTC)

    val recordEndEndDateInstant: Instant =
        if (StringUtils.hasText(recordEndEndDate)) DateParseUtils.stringToInstant(recordEndStartDate!!)
        else LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())
            .atStartOfDay().plusHours(23).plusMinutes(59).plusSeconds(59).toInstant(ZoneOffset.UTC)
}
