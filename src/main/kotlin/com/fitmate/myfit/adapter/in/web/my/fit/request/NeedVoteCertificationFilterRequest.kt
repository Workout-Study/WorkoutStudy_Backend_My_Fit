package com.fitmate.myfit.adapter.`in`.web.my.fit.request

import jakarta.validation.constraints.NotEmpty

data class NeedVoteCertificationFilterRequest(
    @field:NotEmpty val requestUserId: String
)
