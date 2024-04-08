package com.fitmate.myfit.application.port.`in`.my.fit.response

data class FilterCertificationProgressResponseDto(
    val fitGroupId: Long,
    var fitGroupName: String,
    var cycle: Int,
    var frequency: Int,
    var certificationCount: Int
)
