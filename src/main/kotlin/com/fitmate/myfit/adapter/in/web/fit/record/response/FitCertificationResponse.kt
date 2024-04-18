package com.fitmate.myfit.adapter.`in`.web.fit.record.response

import com.fitmate.myfit.domain.CertificationStatus
import java.time.Instant

data class FitCertificationResponse(
    val fitGroupId: Long,
    val fitGroupName: String,
    val fitCertificationId: Long,
    val certificationStatus: CertificationStatus,
    val createdAt: Instant,
    val voteEndDate: Instant
)
