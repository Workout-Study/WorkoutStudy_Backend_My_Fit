package com.fitmate.myfit.application.port.out.fit.record

import com.fitmate.myfit.domain.FitRecord

interface UpdateFitRecordPort {
    fun updateFitRecord(fitRecord: FitRecord)
}