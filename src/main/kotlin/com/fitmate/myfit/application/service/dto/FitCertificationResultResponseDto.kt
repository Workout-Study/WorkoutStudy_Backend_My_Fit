package com.fitmate.myfit.application.service.dto

import com.fitmate.myfit.adapter.out.api.DateParseUtils
import com.fitmate.myfit.domain.CertificationStatus
import java.time.Instant

data class FitCertificationResultResponseDto(
    val fitCertificationId: Long,
    val certificationStatus: CertificationStatus,
    val createdAt: String
) {
    val createdAtInstant: Instant = DateParseUtils.stringToInstant(createdAt)
}