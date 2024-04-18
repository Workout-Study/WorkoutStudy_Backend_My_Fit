package com.fitmate.myfit.application.port.`in`.fit.record.response

import com.fitmate.myfit.adapter.`in`.web.fit.record.response.FitCertificationResponse
import java.time.Instant

data class FitRecordDetailResponseDto(
    val fitRecordId: Long,
    val recordStartDate: Instant,
    val recordEndDate: Instant,
    val createdAt: Instant,
    val multiMediaEndPoints: List<String>?,
    val registeredFitCertifications: List<FitCertificationResponse>
)
