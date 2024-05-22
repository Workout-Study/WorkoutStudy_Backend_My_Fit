package com.fitmate.myfit.adapter.out.persistence.repository

import com.fitmate.myfit.adapter.out.persistence.dto.FitCertificationDetailDto
import com.fitmate.myfit.adapter.out.persistence.dto.FitCertificationWithVoteDto

interface QFitCertificationRepository {
    fun findNeedToVoteCertificationByFitGroupIdAndUserId(
        fitGroupId: Long,
        userId: Int
    ): List<FitCertificationWithVoteDto>

    fun findFitCertificationProgressDetailsByGroupId(
        fitGroupId: Long,
        requestUserId: Int
    ): List<FitCertificationDetailDto>
}