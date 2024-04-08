package com.fitmate.myfit.adapter.`in`.web.certification.request

import jakarta.validation.constraints.NotEmpty

data class RegisterFitCertificationRequest(
    @field:NotEmpty val requestUserId: String,
    val fitRecordId: Long,
    val fitGroupIds: List<Long>
)
