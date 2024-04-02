package com.fitmate.myfit.adapter.out.persistence.repository

import com.fitmate.myfit.adapter.out.persistence.entity.FitGroupForReadEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FitGroupForReadRepository : JpaRepository<FitGroupForReadEntity, Long> {
    fun findByFitGroupId(fitGroupId: Long): Optional<FitGroupForReadEntity>
}