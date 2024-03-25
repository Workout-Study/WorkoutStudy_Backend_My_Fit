package com.fitmate.myfit.adapter.out.persistence.repository

import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FitRecordRepository : JpaRepository<FitRecordEntity, Long> {
}