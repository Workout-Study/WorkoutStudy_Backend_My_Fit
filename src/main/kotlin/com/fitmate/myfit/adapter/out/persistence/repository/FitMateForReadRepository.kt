package com.fitmate.myfit.adapter.out.persistence.repository

import com.fitmate.myfit.adapter.out.persistence.entity.FitMateForReadEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FitMateForReadRepository : JpaRepository<FitMateForReadEntity, Long> {
    fun findByFitGroupIdAndState(fitGroupId: Long, state: Boolean): List<FitMateForReadEntity>
}