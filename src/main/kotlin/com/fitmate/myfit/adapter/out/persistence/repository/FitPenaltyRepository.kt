package com.fitmate.myfit.adapter.out.persistence.repository

import com.fitmate.myfit.adapter.out.persistence.entity.FitPenaltyEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FitPenaltyRepository : JpaRepository<FitPenaltyEntity, Long>, QFitPenaltyRepository {
    fun findByFitPenaltyId(fitPenaltyId: Long): Optional<FitPenaltyEntity>
}