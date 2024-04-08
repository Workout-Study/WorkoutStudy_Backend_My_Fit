package com.fitmate.myfit.application.port.out.fit.record

import com.fitmate.myfit.domain.FitRecordMultiMediaEndPoint

interface RegisterRecordMultiMediaEndPointPort {
    fun saveAll(multiMediaEndPoints: List<FitRecordMultiMediaEndPoint>)
}