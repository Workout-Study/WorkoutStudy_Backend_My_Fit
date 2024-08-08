package com.fitmate.myfit.adapter.`in`.web.fit.record.request

data class UpdateFitRecordMultiMediaEndPointRequest(
    val requestUserId: Int,
    val multiMediaEndPoints: List<String>?
)
