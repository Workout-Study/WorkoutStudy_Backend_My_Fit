package com.fitmate.myfit.application.port.out.user

import com.fitmate.myfit.application.service.dto.UserInfoResponseDto

interface ReadUserPort {
    fun findByUserId(userId: Int): UserInfoResponseDto
}