package com.fitmate.myfit.adapter.`in`.web.certification.response

import com.fitmate.myfit.domain.CertificationStatus
import java.time.Instant

data class FitCertificationDetailResponse(
    val fitGroupId: Long,
    val fitCertificationId: Long,
    val certificationStatus: CertificationStatus,
    val state: Boolean,
    val createdAt: Instant,
    val voteEndDate: Instant
)
