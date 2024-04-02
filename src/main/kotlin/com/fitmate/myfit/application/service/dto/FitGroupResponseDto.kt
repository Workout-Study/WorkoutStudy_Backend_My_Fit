package com.fitmate.myfit.application.service.dto

data class FitGroupResponseDto(
    val fitGroupId: Long,
    val fitGroupName: String,
    val cycle: Int,
    val frequency: Int,
    val state: Boolean
)
