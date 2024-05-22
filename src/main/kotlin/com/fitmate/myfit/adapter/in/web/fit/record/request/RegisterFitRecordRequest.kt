package com.fitmate.myfit.adapter.`in`.web.fit.record.request

import java.time.Instant

data class RegisterFitRecordRequest(
    val requestUserId: Int,
    val recordStartDate: Instant,
    val recordEndDate: Instant,
    val multiMediaEndPoints: List<String>?
)
