package com.fitmate.myfit.application.port.out.fit.group

import com.fitmate.myfit.domain.FitGroupForRead

interface SaveFitGroupForReadPort {
    fun saveFitGroupForRead(fitGroupForRead: FitGroupForRead): FitGroupForRead
}