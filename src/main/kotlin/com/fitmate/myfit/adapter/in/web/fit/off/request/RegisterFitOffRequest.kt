package com.fitmate.myfit.adapter.`in`.web.fit.off.request

import java.time.Instant

data class RegisterFitOffRequest(
    val requestUserId: Int,
    val fitOffStartDate: Instant,
    val fitOffEndDate: Instant,
    val fitOffReason: String
)
