package com.fitmate.myfit.adapter.out.persistence.repository

import com.fitmate.myfit.adapter.out.persistence.entity.FitCertificationEntity
import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.domain.CertificationStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant
import java.util.*

interface FitCertificationRepository : JpaRepository<FitCertificationEntity, Long>, QFitCertificationRepository {
    fun findByFitRecordEntityAndFitGroupIdAndCertificationStatusNotAndState(
        fitRecordEntity: FitRecordEntity,
        fitGroupId: Long,
        certificationStatus: CertificationStatus,
        state: Boolean
    ): List<FitCertificationEntity>

    fun findByUserIdAndFitGroupIdAndCertificationStatusAndState(
        userId: Int,
        fitGroupId: Long,
        certificationStatus: CertificationStatus,
        state: Boolean
    ): Optional<FitCertificationEntity>

    fun findByFitRecordEntityAndCertificationStatusNotAndState(
        fitRecordEntity: FitRecordEntity,
        certificationStatus: CertificationStatus,
        state: Boolean
    ): List<FitCertificationEntity>

    fun countByUserIdAndFitGroupIdAndCertificationStatusAndCreatedAtGreaterThanEqual(
        userId: Int,
        fitGroupId: Long,
        certified: CertificationStatus,
        instant: Instant
    ): Int

    fun findByFitRecordEntityAndState(fitRecordEntity: FitRecordEntity, state: Boolean): List<FitCertificationEntity>
}