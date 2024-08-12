package com.fitmate.myfit.adapter.`in`.web.fit.record.request

data class FitRecordSliceFilterRequest(
    val userId: Int,
    val recordEndStartDate: String? = null,
    val recordEndEndDate: String? = null,
    val pageNumber: Int = 0,
    val pageSize: Int = 5,
)
