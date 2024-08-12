package com.fitmate.myfit.application.port.`in`.my.fit.response

import com.fitmate.myfit.adapter.out.api.DateParseUtils
import java.time.Instant

data class NeedVoteCertificationResponseDto(
    val certificationId: Long,
    val recordId: Long,
    private val recordStartDateInstant: Instant,
    private val recordEndDateInstant: Instant,
    val certificationRequestUserId: Int,
    val certificationRequestUserNickname: String?,
    val agreeCount: Int,
    val disagreeCount: Int,
    val maxAgreeCount: Int,
    private val voteEndDateInstant: Instant,
    val recordMultiMediaEndPoints: List<String>,
) {
    val recordStartDate = DateParseUtils.instantToString(recordStartDateInstant)
    val recordEndDate = DateParseUtils.instantToString(recordEndDateInstant)
    val voteEndDate = DateParseUtils.instantToString(voteEndDateInstant)
}
