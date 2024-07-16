package com.fitmate.myfit.application.port.out.certification

import com.fitmate.myfit.adapter.out.persistence.dto.FitCertificationDetailDto
import com.fitmate.myfit.adapter.out.persistence.dto.FitCertificationWithVoteDto
import com.fitmate.myfit.application.port.`in`.my.fit.command.MyFitGroupIssueSliceFilterCommand
import com.fitmate.myfit.domain.CertificationStatus
import com.fitmate.myfit.domain.FitCertification
import com.fitmate.myfit.domain.FitRecord
import java.time.Instant
import java.util.*

interface ReadFitCertificationPort {
    fun findByFitRecordAndFitGroupIdAndCertificationStatusNot(
        fitRecord: FitRecord,
        fitGroupId: Long,
        certificationStatus: CertificationStatus
    ): List<FitCertification>

    fun findByUserIdAndFitGroupIdAndCertificationStatus(
        userId: Int,
        fitGroupId: Long,
        certificationStatus: CertificationStatus
    ): Optional<FitCertification>

    fun findById(fitCertificationId: Long): Optional<FitCertification>

    fun findByFitRecordAndCertificationStatusNot(
        fitRecord: FitRecord,
        certificationStatus: CertificationStatus
    ): List<FitCertification>

    fun countByUserIdAndFitGroupIdAndCertificationStatusAndDateGreaterThanEqual(
        userId: Int,
        fitGroupId: Long,
        certified: CertificationStatus,
        instant: Instant
    ): Int

    fun findNeedToVoteCertificationByFitGroupIdAndUserId(
        fitGroupId: Long,
        userId: Int
    ): List<FitCertificationWithVoteDto>

    fun findFitCertificationProgressDetailsByGroupId(
        fitGroupId: Long,
        requestUserId: Int
    ): List<FitCertificationDetailDto>

    fun findByFitRecord(fitRecord: FitRecord): List<FitCertification>

    fun findFitCertificationByFitGroupIssue(
        command: MyFitGroupIssueSliceFilterCommand,
        fitGroupIdList: List<Long>,
        fitGroupIssueStartDate: Instant,
        fitGroupIssueEndDate: Instant
    ): List<FitCertification>
}