package com.fitmate.myfit.adapter.out.persistence.repository

import com.fitmate.myfit.adapter.out.persistence.dto.FitCertificationDetailDto
import com.fitmate.myfit.adapter.out.persistence.dto.FitCertificationWithVoteDto
import com.fitmate.myfit.adapter.out.persistence.entity.FitCertificationEntity
import com.fitmate.myfit.application.port.`in`.my.fit.command.MyFitGroupIssueSliceFilterCommand
import java.time.Instant

interface QFitCertificationRepository {
    fun findNeedToVoteCertificationByFitGroupIdAndUserId(
        fitGroupId: Long,
        userId: Int
    ): List<FitCertificationWithVoteDto>

    fun findFitCertificationProgressDetailsByGroupId(
        fitGroupId: Long,
        requestUserId: Int
    ): List<FitCertificationDetailDto>

    fun findFitCertificationByFitGroupIssue(
        command: MyFitGroupIssueSliceFilterCommand,
        fitGroupIdList: List<Long>,
        fitGroupIssueStartDate: Instant,
        fitGroupIssueEndDate: Instant
    ): List<FitCertificationEntity>
}