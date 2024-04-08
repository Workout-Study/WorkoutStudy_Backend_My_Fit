package com.fitmate.myfit.application.service.adapter

import com.fitmate.myfit.adapter.out.api.SenderUtils
import com.fitmate.myfit.adapter.out.api.uris.FitGroupUri
import com.fitmate.myfit.application.port.out.fit.group.ReadFitGroupPort
import com.fitmate.myfit.application.service.dto.FitGroupResponseDto
import com.fitmate.myfit.common.exceptions.NotExpectResultException
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class FitGroupApiAdapter(private val senderUtils: SenderUtils) : ReadFitGroupPort {

    override fun findByFitGroupId(fitGroupId: Long): FitGroupResponseDto {
        val uriEndPoint = "${FitGroupUri.GROUP_ROOT}/${fitGroupId}"

        val response: ResponseEntity<FitGroupResponseDto> =
            senderUtils.send(
                HttpMethod.GET,
                uriEndPoint,
                null,
                null,
                object : ParameterizedTypeReference<FitGroupResponseDto>() {
                })

        return response.body ?: throw NotExpectResultException("fit group response body is null")
    }
}