package com.fitmate.myfit.adapter.`in`.web.fit.record.api

import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.fit.record.mapper.FitRecordDtoMapper
import com.fitmate.myfit.adapter.`in`.web.fit.record.request.DeleteFitRecordRequest
import com.fitmate.myfit.adapter.`in`.web.fit.record.request.RegisterFitRecordRequest
import com.fitmate.myfit.adapter.`in`.web.fit.record.response.DeleteFitRecordResponse
import com.fitmate.myfit.adapter.`in`.web.fit.record.response.RegisterFitRecordResponse
import com.fitmate.myfit.application.port.`in`.fit.record.usecase.DeleteFitRecordUseCase
import com.fitmate.myfit.application.port.`in`.fit.record.usecase.RegisterFitRecordUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class FitRecordController(
    private val registerFitRecordUseCase: RegisterFitRecordUseCase,
    private val deleteFitRecordUseCase: DeleteFitRecordUseCase
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

    /**
     * Delete fit record inbound adapter
     *
     * @param fitRecordId request delete fit record id
     * @param deleteFitRecordRequest request user id
     * @return Boolean about delete success
     */
    @DeleteMapping(GlobalURI.FIT_RECORD_ROOT + GlobalURI.PATH_VARIABLE_FIT_RECORD_ID_WITH_BRACE)
    fun deleteFitRecord(
        @PathVariable(GlobalURI.PATH_VARIABLE_FIT_RECORD_ID) fitRecordId: Long,
        @RequestBody @Valid deleteFitRecordRequest: DeleteFitRecordRequest
    ): ResponseEntity<DeleteFitRecordResponse> {
        val deleteFitRecordCommand = FitRecordDtoMapper.deleteRequestToCommand(fitRecordId, deleteFitRecordRequest)
        val deleteFitRecordResponseDto = deleteFitRecordUseCase.deleteFitRecord(deleteFitRecordCommand)
        return ResponseEntity.ok()
            .body(FitRecordDtoMapper.dtoToDeleteResponse(deleteFitRecordResponseDto))
    }
}