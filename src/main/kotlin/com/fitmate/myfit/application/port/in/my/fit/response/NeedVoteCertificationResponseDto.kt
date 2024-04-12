package com.fitmate.myfit.application.port.`in`.my.fit.response

import java.time.Instant

data class NeedVoteCertificationResponseDto(
    val certificationId: Long,
    val recordId: Long,
    val certificationRequestUserId: String,
    val agreeCount: Int,
    val disagreeCount: Int,
    val maxAgreeCount: Int,
    val voteEndDate: Instant,
    val recordMultiMediaEndPoints: List<String>,
)
