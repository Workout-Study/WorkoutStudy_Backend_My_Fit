package com.fitmate.myfit.application.service.adapter

import com.fitmate.myfit.adapter.out.api.SenderUtils
import com.fitmate.myfit.adapter.out.api.uris.FitGroupServiceURI
import com.fitmate.myfit.application.port.out.fit.mate.ReadFitMatePort
import com.fitmate.myfit.application.service.dto.FitMateDetailsResponseDto
import com.fitmate.myfit.common.exceptions.NotExpectResultException
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class FitMateApiAdapter(private val senderUtils: SenderUtils) : ReadFitMatePort {

    override fun findByFitGroupId(fitGroupId: Long): FitMateDetailsResponseDto {
        val uriEndPoint = "${FitGroupServiceURI.MATE_ROOT}/${fitGroupId}"

        val response: ResponseEntity<FitMateDetailsResponseDto> =
            senderUtils.send(
                HttpMethod.GET,
                uriEndPoint,
                null,
                null,
                object : ParameterizedTypeReference<FitMateDetailsResponseDto>() {
                })

        return response.body ?: throw NotExpectResultException("fit group response body is null")
    }
}