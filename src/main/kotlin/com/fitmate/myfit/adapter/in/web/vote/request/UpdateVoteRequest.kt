package com.fitmate.myfit.adapter.`in`.web.vote.request

import jakarta.validation.constraints.NotEmpty

data class UpdateVoteRequest(
    @field:NotEmpty val requestUserId: String,
    var agree: Boolean,
    val targetCategory: Int,
    val targetId: Long
)
