package com.fitmate.myfit.application.port.out.fit.penalty

import com.fitmate.myfit.domain.FitPenalty

interface SaveFitPenaltyPort {
    fun saveFitPenalty(fitPenalty: FitPenalty): FitPenalty
}