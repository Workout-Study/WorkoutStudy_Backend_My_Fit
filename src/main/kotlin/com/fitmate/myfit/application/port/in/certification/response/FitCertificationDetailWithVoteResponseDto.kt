package com.fitmate.myfit.application.port.`in`.certification.response

import java.time.Instant

data class FitCertificationDetailWithVoteResponseDto(
    val certificationId: Long,
    val recordId: Long,
    val certificationRequestUserId: Int,
    val certificationRequestUserNickname: String?,
    val isUserVoteDone: Boolean,
    val isUserAgree: Boolean,
    val agreeCount: Int,
    val disagreeCount: Int,
    val maxAgreeCount: Int,
    val fitRecordStartDate: Instant,
    val fitRecordEndDate: Instant,
    val multiMediaEndPoints: List<String>?,
    val voteEndDate: Instant
)
