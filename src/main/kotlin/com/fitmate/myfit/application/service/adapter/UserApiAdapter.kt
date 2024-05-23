package com.fitmate.myfit.application.service.adapter

import com.fitmate.myfit.adapter.out.api.SenderUtils
import com.fitmate.myfit.adapter.out.api.uris.UserServiceURI
import com.fitmate.myfit.application.port.out.user.ReadUserPort
import com.fitmate.myfit.application.service.dto.UserInfoResponseDto
import com.fitmate.myfit.common.exceptions.NotExpectResultException
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class UserApiAdapter(private val senderUtils: SenderUtils) : ReadUserPort {

    override fun findByUserId(userId: Int): UserInfoResponseDto {
        val uriEndPoint = "${UserServiceURI.USER_INFO}?userId=${userId}"

        val response: ResponseEntity<UserInfoResponseDto> =
            senderUtils.send(
                HttpMethod.GET,
                uriEndPoint,
                null,
                null,
                object : ParameterizedTypeReference<UserInfoResponseDto>() {
                })

        return response.body ?: throw NotExpectResultException("user info response body is null")
    }
}