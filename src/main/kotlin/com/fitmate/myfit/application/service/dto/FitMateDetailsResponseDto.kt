package com.fitmate.myfit.application.service.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class FitMateDetailsResponseDto(
    val fitGroupId: Long,
    val fitMateDetails: List<FitMateResponseDto>
)
