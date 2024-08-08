package com.fitmate.myfit.adapter.out.persistence.repository

import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordMultiMediaEndPointEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FitRecordMultiMediaEndPointRepository : JpaRepository<FitRecordMultiMediaEndPointEntity, Long> {
    fun findByFitRecordEntityAndStateOrderByIdAsc(
        fitRecordEntity: FitRecordEntity,
        state: Boolean
    ): List<FitRecordMultiMediaEndPointEntity>

    fun findByFitRecordEntityAndState(
        fitRecordEntity: FitRecordEntity,
        state: Boolean
    ): List<FitRecordMultiMediaEndPointEntity>
}