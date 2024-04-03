package com.fitmate.myfit.application.service.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class FitMateResponseDto(
    val fitMateId: Long,
    val fitMateUserId: String
)
