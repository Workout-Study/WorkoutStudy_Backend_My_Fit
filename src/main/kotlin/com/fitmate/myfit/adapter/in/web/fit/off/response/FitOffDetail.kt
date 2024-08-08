package com.fitmate.myfit.adapter.`in`.web.fit.off.response

import java.time.Instant

data class FitOffDetail(
    val fitOffId: Long,
    val userId: Int,
    var fitOffStartDate: Instant,
    var fitOffEndDate: Instant,
    var fitOffReason: String
)
