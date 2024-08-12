package com.fitmate.myfit.adapter.`in`.web.fit.record.request

data class RegisterFitRecordRequest(
    val requestUserId: Int,
    val recordStartDate: String,
    val recordEndDate: String,
    val multiMediaEndPoints: List<String>?
)
