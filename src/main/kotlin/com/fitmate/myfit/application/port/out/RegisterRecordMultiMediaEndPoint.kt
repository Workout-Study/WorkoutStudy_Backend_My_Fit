package com.fitmate.myfit.application.port.out

import com.fitmate.myfit.domain.FitRecordMultiMediaEndPoint

interface RegisterRecordMultiMediaEndPoint {
    fun saveAll(multiMediaEndPoints: List<FitRecordMultiMediaEndPoint>)
}