package com.fitmate.myfit.adapter.out.persistence.repository

import com.fitmate.myfit.adapter.out.persistence.dto.FitCertificationWithVoteDto

interface QFitCertificationRepository {
    fun findNeedToVoteCertificationByFitGroupIdAndUserId(
        fitGroupId: Long,
        userId: String
    ): List<FitCertificationWithVoteDto>
}