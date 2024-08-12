package com.fitmate.myfit.adapter.`in`.web.fit.record.response

import com.fitmate.myfit.adapter.out.api.DateParseUtils
import com.fitmate.myfit.domain.CertificationStatus
import java.time.Instant

data class FitCertificationResponse(
    val fitGroupId: Long,
    val fitGroupName: String,
    val fitCertificationId: Long,
    val certificationStatus: CertificationStatus,
    private val createdAtInstant: Instant,
    private val voteEndDateInstant: Instant
) {
    val createdAt = DateParseUtils.instantToString(createdAtInstant)
    val voteEndDate = DateParseUtils.instantToString(voteEndDateInstant)
}
