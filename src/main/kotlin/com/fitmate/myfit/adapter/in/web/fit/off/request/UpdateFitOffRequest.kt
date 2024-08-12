package com.fitmate.myfit.adapter.`in`.web.fit.off.request

import jakarta.validation.constraints.NotEmpty

data class UpdateFitOffRequest(
    val requestUserId: Int,
    @field:NotEmpty val fitOffStartDate: String,
    @field:NotEmpty val fitOffEndDate: String,
    val fitOffReason: String
)
