package com.fitmate.myfit.application.port.`in`.fit.off.command

import java.time.Instant

data class UpdateFitOffCommand(
    val fitOffId: Long,
    val requestUserId: Int,
    val fitOffStartDate: Instant,
    val fitOffEndDate: Instant,
    val fitOffReason: String
)
