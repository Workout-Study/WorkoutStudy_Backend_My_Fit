package com.fitmate.myfit.application.port.out.fit.mate

import com.fitmate.myfit.domain.FitMateForRead

interface SaveFitMateForReadPort {
    fun saveFitMate(fitMateForRead: FitMateForRead)
}