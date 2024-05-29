package com.fitmate.myfit.application.port.out.fit.off

import com.fitmate.myfit.domain.FitOff
import java.time.Instant
import java.util.*

interface ReadFitOffPort {

    fun countByUserIdAndFitOffDate(userId: Int, fitOffStartDate: Instant, fitOffEndDate: Instant): Int
    fun findById(fitOffId: Long): Optional<FitOff>
}