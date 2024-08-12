package com.fitmate.myfit.application.port.`in`.certification.response

import com.fitmate.myfit.adapter.out.api.DateParseUtils
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
    private val fitRecordStartDateInstant: Instant,
    private val fitRecordEndDateInstant: Instant,
    val multiMediaEndPoints: List<String>?,
    private val voteEndDateInstant: Instant
) {
    val fitRecordStartDate = DateParseUtils.instantToString(fitRecordStartDateInstant)
    val fitRecordEndDate = DateParseUtils.instantToString(fitRecordStartDateInstant)
    val voteEndDate = DateParseUtils.instantToString(voteEndDateInstant)
}
