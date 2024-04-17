package com.fitmate.myfit.application.port.out.certification

import com.fitmate.myfit.adapter.out.persistence.dto.FitCertificationDetailDto
import com.fitmate.myfit.adapter.out.persistence.dto.FitCertificationWithVoteDto
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
        userId: String,
        fitGroupId: Long,
        certificationStatus: CertificationStatus
    ): Optional<FitCertification>

    fun findById(fitCertificationId: Long): Optional<FitCertification>

    fun findByFitRecordAndCertificationStatusNot(
        fitRecord: FitRecord,
        certificationStatus: CertificationStatus
    ): List<FitCertification>

    fun countByUserIdAndFitGroupIdAndCertificationStatusAndDateGreaterThanEqual(
        userId: String,
        fitGroupId: Long,
        certified: CertificationStatus,
        instant: Instant
    ): Int

    fun findNeedToVoteCertificationByFitGroupIdAndUserId(
        fitGroupId: Long,
        userId: String
    ): List<FitCertificationWithVoteDto>

    fun findFitCertificationProgressDetailsByGroupId(
        fitGroupId: Long,
        requestUserId: String
    ): List<FitCertificationDetailDto>
}