package com.fitmate.myfit.application.port.out.certification

import com.fitmate.myfit.domain.FitRecord

interface RegisterFitRecordPort {
    fun registerFitRecord(fitRecord: FitRecord): FitRecord
}