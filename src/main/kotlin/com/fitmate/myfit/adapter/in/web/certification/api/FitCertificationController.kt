package com.fitmate.myfit.adapter.`in`.web.certification.api

import com.fitmate.myfit.adapter.`in`.web.certification.mapper.FitCertificationDtoMapper
import com.fitmate.myfit.adapter.`in`.web.certification.request.RegisterFitCertificationRequest
import com.fitmate.myfit.adapter.`in`.web.certification.response.RegisterFitCertificationResponse
import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.application.port.`in`.certification.usecase.RegisterFitCertificationUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FitCertificationController(
    private val registerFitCertificationUseCase: RegisterFitCertificationUseCase
) {

    /**
     * Register fit certification inbound adapter
     *
     * @param registerFitCertificationRequest data about register fit certification
     * @return Boolean about register success
     */
    @PostMapping(GlobalURI.FIT_CERTIFICATION_ROOT)
    fun registerFitCertification(@RequestBody @Valid registerFitCertificationRequest: RegisterFitCertificationRequest): ResponseEntity<RegisterFitCertificationResponse> {
        val registerFitCertificationCommand =
            FitCertificationDtoMapper.registerRequestToCommand(registerFitCertificationRequest)
        val registerFitCertificationResponseDto =
            registerFitCertificationUseCase.registerFitCertification(registerFitCertificationCommand)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(FitCertificationDtoMapper.dtoToRegisterResponse(registerFitCertificationResponseDto))
    }
}