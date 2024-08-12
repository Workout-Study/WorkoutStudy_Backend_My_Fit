package com.fitmate.myfit.adapter.`in`.web.certification.response

import com.fitmate.myfit.adapter.out.api.DateParseUtils
import com.fitmate.myfit.domain.CertificationStatus
import java.time.Instant

data class FitCertificationDetailResponse(
    val fitGroupId: Long,
    val fitCertificationId: Long,
    val certificationStatus: CertificationStatus,
    val state: Boolean,
    private val createdAtInstant: Instant,
    private val voteEndDateInstant: Instant
) {
    val createdAt = DateParseUtils.instantToString(createdAtInstant)
    val voteEndDate = DateParseUtils.instantToString(voteEndDateInstant)
}
