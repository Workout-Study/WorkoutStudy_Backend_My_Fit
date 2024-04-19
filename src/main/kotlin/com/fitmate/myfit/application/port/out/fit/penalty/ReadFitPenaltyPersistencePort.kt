package com.fitmate.myfit.application.port.out.fit.penalty

import com.fitmate.myfit.domain.FitPenalty
import java.util.*

interface ReadFitPenaltyPersistencePort {
    fun findByFitPenaltyId(fitPenaltyId: Long): Optional<FitPenalty>
}