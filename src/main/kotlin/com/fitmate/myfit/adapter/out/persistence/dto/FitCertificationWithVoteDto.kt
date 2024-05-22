package com.fitmate.myfit.adapter.out.persistence.dto

import com.fitmate.myfit.domain.CertificationStatus
import java.time.Instant

data class FitCertificationWithVoteDto(
    val certificationId: Long,
    val userId: Int,
    val fitRecordId: Long,
    val certificationStatus: CertificationStatus,
    val agreeCount: Long,
    val disagreeCount: Long,
    val maxAgreeCount: Long,
    val createdAt: Instant
)
