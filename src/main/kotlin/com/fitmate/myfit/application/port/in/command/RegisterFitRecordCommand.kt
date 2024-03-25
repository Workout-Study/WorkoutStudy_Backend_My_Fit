package com.fitmate.myfit.application.port.`in`.command

import jakarta.validation.constraints.NotEmpty
import java.time.Instant

data class RegisterFitRecordCommand(
    @field:NotEmpty val requestUserId: String,
    val recordStartDate: Instant,
    val recordEndDate: Instant,
    val multiMediaEndPoints: List<String>?
) : SelfValidating<RegisterFitRecordCommand>() {

    init {
        this.validateSelf()
    }
}