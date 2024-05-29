package com.fitmate.myfit.application.port.out.fit.off

import com.fitmate.myfit.domain.FitOff

interface UpdateFitOffPort {
    fun updateFitOff(fitOff: FitOff)
}