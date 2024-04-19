package com.fitmate.myfit.adapter.`in`.web.penalty.response

import com.fitmate.myfit.application.port.`in`.fit.penalty.response.FitPenaltyFilteredResponseDto

data class FitPenaltyFilteredResponse(
    val pageNumber: Int,
    val pageSize: Int,
    val hasNext: Boolean,
    val totalAmount: Int,
    val content: List<FitPenaltyFilteredResponseDto>
)
