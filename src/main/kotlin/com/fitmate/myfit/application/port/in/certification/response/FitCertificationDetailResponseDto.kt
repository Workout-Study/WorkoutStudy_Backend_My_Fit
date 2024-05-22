package com.fitmate.myfit.application.port.`in`.certification.response

import com.fitmate.myfit.domain.CertificationStatus
import java.time.Instant

data class FitCertificationDetailResponseDto(
    val fitGroupId: Long,
    val fitCertificationId: Long,
    val userId: Int,
    val certificationStatus: CertificationStatus,
    val state: Boolean,
    val createdAt: Instant,
    val voteEndDate: Instant
)
