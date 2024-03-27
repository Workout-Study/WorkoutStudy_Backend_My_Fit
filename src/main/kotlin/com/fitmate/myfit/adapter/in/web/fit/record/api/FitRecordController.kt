package com.fitmate.myfit.adapter.`in`.web.fit.record.api

import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.fit.record.mapper.FitRecordDtoMapper
import com.fitmate.myfit.adapter.`in`.web.fit.record.request.RegisterFitRecordRequest
import com.fitmate.myfit.adapter.`in`.web.fit.record.response.RegisterFitRecordResponse
import com.fitmate.myfit.application.port.`in`.fit.record.usecase.RegisterFitRecordUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FitRecordController(
    private val registerFitRecordUseCase: RegisterFitRecordUseCase
) {

    /**
     * Register fit record inbound adapter
     *
     * @param registerFitRecordRequest data about register fitRecord
     * @return Boolean about register success
     */
    @PostMapping(GlobalURI.FIT_RECORD_ROOT)
    fun registerFitRecord(
        @RequestBody @Valid registerFitRecordRequest: RegisterFitRecordRequest
    ): ResponseEntity<RegisterFitRecordResponse> {
        val registerFitRecordCommand = FitRecordDtoMapper.registerRequestToCommand(registerFitRecordRequest)
        val registerFitRecordResponseDto = registerFitRecordUseCase.registerFitRecord(registerFitRecordCommand)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(FitRecordDtoMapper.dtoToRegisterResponse(registerFitRecordResponseDto))
    }
}