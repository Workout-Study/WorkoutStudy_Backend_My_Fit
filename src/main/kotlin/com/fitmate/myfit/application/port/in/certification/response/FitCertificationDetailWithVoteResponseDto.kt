package com.fitmate.myfit.application.port.`in`.certification.response

import java.time.Instant

data class FitCertificationDetailWithVoteResponseDto(
    val certificationId: Long,
    val recordId: Long,
    val certificationRequestUserId: String,
    val isUserVoteDone: Boolean,
    val isUserAgree: Boolean,
    val agreeCount: Int,
    val disagreeCount: Int,
    val maxAgreeCount: Int,
    val fitRecordStartDate: Instant,
    val fitRecordEndDate: Instant,
    val thumbnailEndPoint: String?,
    val voteEndDate: Instant
)
