package com.fitmate.myfit.application.port.`in`.my.fit.response

import java.time.Instant

data class NeedVoteCertificationResponseDto(
    val certificationId: Long,
    val agreeCount: Int,
    val disagreeCount: Int,
    val maxAgreeCount: Int,
    val voteEndDate: Instant
)
