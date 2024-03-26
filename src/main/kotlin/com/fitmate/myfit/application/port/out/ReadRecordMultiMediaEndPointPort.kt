package com.fitmate.myfit.application.port.out

import com.fitmate.myfit.domain.FitRecord
import com.fitmate.myfit.domain.FitRecordMultiMediaEndPoint

interface ReadRecordMultiMediaEndPointPort {
    fun findByFitRecordAndOrderByIdAsc(fitRecord: FitRecord): List<FitRecordMultiMediaEndPoint>
}