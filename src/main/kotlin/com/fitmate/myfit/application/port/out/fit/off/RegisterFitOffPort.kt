package com.fitmate.myfit.application.port.out.fit.off

import com.fitmate.myfit.domain.FitOff

interface RegisterFitOffPort {
    fun registerFitOff(fitOff: FitOff): FitOff
}