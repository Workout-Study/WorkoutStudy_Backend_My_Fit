package com.fitmate.myfit.application.service.dto

import com.fitmate.myfit.domain.CertificationStatus
import java.time.Instant

data class FitCertificationResultResponseDto(
    val fitCertificationId: Long,
    val certificationStatus: CertificationStatus,
    val createdAt: Instant
)