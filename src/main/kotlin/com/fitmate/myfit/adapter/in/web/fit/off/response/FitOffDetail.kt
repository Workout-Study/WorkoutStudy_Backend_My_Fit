package com.fitmate.myfit.adapter.`in`.web.fit.off.response

import com.fitmate.myfit.adapter.out.api.DateParseUtils
import java.time.Instant

data class FitOffDetail(
    val fitOffId: Long,
    val userId: Int,
    private val fitOffStartDateInstant: Instant,
    private val fitOffEndDateInstant: Instant,
    var fitOffReason: String
) {
    val fitOffStartDate = DateParseUtils.instantToString(fitOffStartDateInstant)
    val fitOffEndDate = DateParseUtils.instantToString(fitOffEndDateInstant)
}
