package com.fitmate.myfit.application.port.`in`.fit.record.response

import com.fitmate.myfit.adapter.`in`.web.fit.record.response.FitCertificationResponse
import com.fitmate.myfit.adapter.out.api.DateParseUtils
import java.time.Instant

data class FitRecordDetailResponseDto(
    val fitRecordId: Long,
    private val recordStartDateInstant: Instant,
    private val recordEndDateInstant: Instant,
    private val createdAtInstant: Instant,
    val multiMediaEndPoints: List<String>?,
    val registeredFitCertifications: List<FitCertificationResponse>
) {
    val createdAt = DateParseUtils.instantToString(createdAtInstant)
    val recordStartDate = DateParseUtils.instantToString(recordStartDateInstant)
    val recordEndDate = DateParseUtils.instantToString(recordEndDateInstant)
}
