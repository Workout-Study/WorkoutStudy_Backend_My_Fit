package com.fitmate.myfit.application.service.adapter

import com.fitmate.myfit.adapter.out.api.SenderUtils
import com.fitmate.myfit.adapter.out.api.uris.BatchServiceURI
import com.fitmate.myfit.application.port.out.fit.penalty.ReadFitPenaltyApiPort
import com.fitmate.myfit.application.service.dto.FitPenaltyDetailResponseDto
import com.fitmate.myfit.common.exceptions.NotExpectResultException
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class ReadFitPenaltyApiAdapter(
    private val senderUtils: SenderUtils
) : ReadFitPenaltyApiPort {

    override fun findByFitPenaltyId(fitPenaltyId: Long): FitPenaltyDetailResponseDto {
        val uriEndPoint = "${BatchServiceURI.FIT_PENALTY_ROOT}/${fitPenaltyId}"

        val response: ResponseEntity<FitPenaltyDetailResponseDto> =
            senderUtils.send(
                HttpMethod.GET,
                uriEndPoint,
                null,
                null,
                object : ParameterizedTypeReference<FitPenaltyDetailResponseDto>() {
                })

        return response.body ?: throw NotExpectResultException("fit penalty response body is null")
    }
}