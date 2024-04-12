package com.fitmate.myfit.application.port.`in`.my.fit.response

data class FilterCertificationProgressResponseDto(
    val fitGroupId: Long,
    val fitGroupName: String,
    val thumbnailEndPoint: String?,
    val cycle: Int,
    val frequency: Int,
    val certificationCount: Int
)
