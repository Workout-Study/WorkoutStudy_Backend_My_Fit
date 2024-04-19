package com.fitmate.myfit.application.service.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class FitGroupResponseDto(
    val fitGroupId: Long,
    val fitGroupName: String,
    val fitLeaderUserId: String,
    val cycle: Int,
    val frequency: Int,
    val multiMediaEndPoints: List<String>,
    val state: Boolean
)
