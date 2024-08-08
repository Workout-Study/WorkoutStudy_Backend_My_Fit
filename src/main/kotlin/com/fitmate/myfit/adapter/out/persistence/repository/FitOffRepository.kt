package com.fitmate.myfit.adapter.out.persistence.repository

import com.fitmate.myfit.adapter.out.persistence.entity.FitOffEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant
import java.util.*

interface FitOffRepository : JpaRepository<FitOffEntity, Long> {
    fun countByUserIdAndFitOffStartDateBetweenAndFitOffEndDateBetweenAndState(
        userId: Int,
        fitOffStartDateForFitOffStartDate: Instant,
        fitOffEndDateForFitOffStartDate: Instant,
        fitOffStartDateForFitOffEndDate: Instant,
        fitOffEndDateForFitOffEndDate: Instant,
        state: Boolean
    ): Int

    fun findByIdAndState(fitOffId: Long, state: Boolean): Optional<FitOffEntity>
    fun findByUserIdInAndStateAndFitOffStartDateLessThanEqualAndFitOffEndDateGreaterThanEqual(
        userIdList: List<Int>,
        state: Boolean,
        startDate: Instant,
        endDate: Instant
    ): List<FitOffEntity>
}