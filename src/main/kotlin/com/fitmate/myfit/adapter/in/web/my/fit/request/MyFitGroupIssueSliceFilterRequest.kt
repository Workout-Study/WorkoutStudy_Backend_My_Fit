package com.fitmate.myfit.adapter.`in`.web.my.fit.request

data class MyFitGroupIssueSliceFilterRequest(
    val userId: Int,
    val pageNumber: Int = 0,
    val pageSize: Int = 5,
)
