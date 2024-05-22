package com.fitmate.myfit.adapter.`in`.web.vote.request

data class DeleteVoteRequest(
    val requestUserId: Int,
    val targetCategory: Int,
    val targetId: Long
)
