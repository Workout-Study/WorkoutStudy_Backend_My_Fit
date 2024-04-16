package com.fitmate.myfit.application.port.`in`.certification.response

import com.fitmate.myfit.domain.CertificationStatus
import java.time.Instant

data class FitCertificationDetailResponseDto(
    val fitCertificationId: Long,
    val certificationStatus: CertificationStatus,
    val state: Boolean,
    val createdAt: Instant,
    val voteEndDate: Instant
)
