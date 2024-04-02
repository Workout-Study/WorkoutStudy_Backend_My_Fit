package com.fitmate.myfit.adapter.`in`.web.vote.request

import jakarta.validation.constraints.NotEmpty

data class DeleteVoteRequest(
    @field:NotEmpty val requestUserId: String,
    val targetCategory: Int,
    val targetId: Long
)
