package com.fitmate.myfit.adapter.out.persistence.repository

import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FitRecordRepository : JpaRepository<FitRecordEntity, Long>, QFitRecordRepository {
    fun findByIdAndState(fitRecordId: Long, persistenceNotDeleted: Boolean): Optional<FitRecordEntity>
}