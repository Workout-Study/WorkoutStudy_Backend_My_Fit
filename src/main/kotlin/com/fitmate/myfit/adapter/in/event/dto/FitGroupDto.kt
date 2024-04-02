package com.fitmate.myfit.adapter.`in`.event.dto

data class FitGroupDto(
    val fitGroupId: Long,
    val fitGroupName: String,
    val cycle: Int,
    val frequency: Int,
    val state: Boolean
)
