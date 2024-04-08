package com.fitmate.myfit.application.port.`in`.fit.record.response

import java.time.Instant

data class FitRecordDetailResponseDto(
    val fitRecordId: Long,
    val recordStartDate: Instant,
    val recordEndDate: Instant,
    val createdAt: Instant,
    val multiMediaEndPoints: List<String>?
)
