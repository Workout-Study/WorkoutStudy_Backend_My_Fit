package com.fitmate.myfit.adapter.out.persistence.dto

import java.time.Instant

data class FitCertificationDetailDto(
    val certificationId: Long,
    val recordId: Long,
    val certificationRequestUserId: String,
    val isUserVoteDone: Boolean,
    val isUserAgree: Boolean,
    val agreeCount: Long,
    val disagreeCount: Long,
    val maxAgreeCount: Long,
    val fitRecordStartDate: Instant,
    val fitRecordEndDate: Instant,
    val createdAt: Instant
)
