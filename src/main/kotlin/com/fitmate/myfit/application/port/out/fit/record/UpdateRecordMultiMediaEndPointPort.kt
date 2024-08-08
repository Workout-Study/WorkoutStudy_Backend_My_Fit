package com.fitmate.myfit.application.port.out.fit.record

import com.fitmate.myfit.domain.FitRecordMultiMediaEndPoint

interface UpdateRecordMultiMediaEndPointPort {
    fun update(fitRecordMultiMediaEndPoint: FitRecordMultiMediaEndPoint)
}