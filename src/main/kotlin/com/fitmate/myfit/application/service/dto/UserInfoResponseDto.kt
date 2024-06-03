package com.fitmate.myfit.application.service.dto

import java.time.Instant

data class UserInfoResponseDto(
    val userId: Int,
    val nickname: String,
    val state: Boolean,
    val createdAt: Instant,
    val updatedAt: Instant
)
