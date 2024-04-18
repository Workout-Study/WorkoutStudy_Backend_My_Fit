package com.fitmate.myfit.application.port.out.fit.mate

import com.fitmate.myfit.domain.FitMateForRead

interface ReadFitMateForReadPort {
    fun findByFitGroupId(fitGroupId: Long): List<FitMateForRead>
    fun findByFitMateUserId(userId: String): List<FitMateForRead>
    fun countByFitGroup(fitGroupId: Long): Int
}