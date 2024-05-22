package com.fitmate.myfit.adapter.`in`.web.vote.request

data class UpdateVoteRequest(
    val requestUserId: Int,
    var agree: Boolean,
    val targetCategory: Int,
    val targetId: Long
)
