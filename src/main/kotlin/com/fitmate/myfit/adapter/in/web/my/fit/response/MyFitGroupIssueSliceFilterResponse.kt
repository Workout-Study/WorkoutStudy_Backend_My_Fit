package com.fitmate.myfit.adapter.`in`.web.my.fit.response

import com.fitmate.myfit.application.port.`in`.my.fit.response.MyFitGroupIssueSliceFilterResponseDto

data class MyFitGroupIssueSliceFilterResponse(
    val pageNumber: Int,
    val pageSize: Int,
    val hasNext: Boolean,
    val content: List<MyFitGroupIssueSliceFilterResponseDto>
)
