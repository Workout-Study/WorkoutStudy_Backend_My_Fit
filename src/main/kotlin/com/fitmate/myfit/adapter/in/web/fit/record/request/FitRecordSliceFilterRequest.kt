package com.fitmate.myfit.adapter.`in`.web.fit.record.request

import java.time.Instant

data class FitRecordSliceFilterRequest(
    val userId: Int,
    val recordEndStartDate: Instant? = null,
    val recordEndEndDate: Instant? = null,
    val pageNumber: Int = 0,
    val pageSize: Int = 5,
)
