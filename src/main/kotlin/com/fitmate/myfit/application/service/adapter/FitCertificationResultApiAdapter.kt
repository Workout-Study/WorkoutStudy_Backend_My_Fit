package com.fitmate.myfit.application.service.adapter

import com.fitmate.myfit.adapter.out.api.SenderUtils
import com.fitmate.myfit.adapter.out.api.uris.BatchServiceURI
import com.fitmate.myfit.application.port.out.certification.ReadFitCertificationResultPort
import com.fitmate.myfit.application.service.dto.FitCertificationResultResponseDto
import com.fitmate.myfit.common.exceptions.NotExpectResultException
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class FitCertificationResultApiAdapter(
    private val senderUtils: SenderUtils
) : ReadFitCertificationResultPort {

    override fun findByFitCertificationId(fitCertificationId: Long): FitCertificationResultResponseDto {
        val uriEndPoint = "${BatchServiceURI.FIT_CERTIFICATION_RESULT_ROOT}/${fitCertificationId}"

        val response: ResponseEntity<FitCertificationResultResponseDto> =
            senderUtils.send(
                HttpMethod.GET,
                uriEndPoint,
                null,
                null,
                object : ParameterizedTypeReference<FitCertificationResultResponseDto>() {
                })

        return response.body ?: throw NotExpectResultException("fit certification result response body is null")
    }
}