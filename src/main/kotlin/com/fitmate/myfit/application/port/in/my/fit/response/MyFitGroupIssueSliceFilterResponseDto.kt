package com.fitmate.myfit.application.port.`in`.my.fit.response

import com.fitmate.myfit.domain.CertificationStatus
import java.time.Instant

data class MyFitGroupIssueSliceFilterResponseDto(
    val fitGroupId: Long,
    val certificationRequestUserId: Int,
    val certificationRequestUserNickname: String?,
    val thumbnailEndPoint: String?,
    val certificationStatus: CertificationStatus,
    val agreeCount: Int,
    val disagreeCount: Int,
    val maxAgreeCount: Int,
    val isUserVoteDone: Boolean,
    val isUserAgree: Boolean,
    val issueDate: Instant
)
