package com.fitmate.myfit.adapter.`in`.web.management.api

import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.management.mapper.FitManagementDtoMapper
import com.fitmate.myfit.adapter.`in`.web.management.request.NoNeedPayFitPenaltyRequest
import com.fitmate.myfit.adapter.`in`.web.management.request.PaidFitPenaltyRequest
import com.fitmate.myfit.adapter.`in`.web.management.response.NoNeedPayFitPenaltyResponse
import com.fitmate.myfit.adapter.`in`.web.management.response.PaidFitPenaltyResponse
import com.fitmate.myfit.application.port.`in`.management.usecase.UpdateFitPenaltyUseCase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class FitManagementController(
    private val updateFitPenaltyUseCase: UpdateFitPenaltyUseCase
) {

    @PutMapping(GlobalURI.FIT_PENALTY_MANAGEMENT + GlobalURI.PATH_VARIABLE_FIT_PENALTY_ID_WITH_BRACE)
    fun paidFitPenaltyByFitReader(
        @PathVariable(GlobalURI.PATH_VARIABLE_FIT_PENALTY_ID) fitPenaltyId: Long,
        @RequestBody @Valid paidFitPenaltyRequest: PaidFitPenaltyRequest
    ): ResponseEntity<PaidFitPenaltyResponse> {
        val paidFitPenaltyCommand =
            FitManagementDtoMapper.paidFitPenaltyRequestToCommand(fitPenaltyId, paidFitPenaltyRequest)
        val paidFitPenaltyResponseDto = updateFitPenaltyUseCase.paidFitPenaltyByFitReader(paidFitPenaltyCommand)
        return ResponseEntity.ok().body(FitManagementDtoMapper.dtoToPaidFitPenaltyResponse(paidFitPenaltyResponseDto))
    }

    @DeleteMapping(GlobalURI.FIT_PENALTY_MANAGEMENT + GlobalURI.PATH_VARIABLE_FIT_PENALTY_ID_WITH_BRACE)
    fun noNeedPayFitPenaltyByFitReader(
        @PathVariable(GlobalURI.PATH_VARIABLE_FIT_PENALTY_ID) fitPenaltyId: Long,
        @RequestBody @Valid noNeedPayFitPenaltyRequest: NoNeedPayFitPenaltyRequest
    ): ResponseEntity<NoNeedPayFitPenaltyResponse> {
        val noNeedFitPenaltyCommand =
            FitManagementDtoMapper.noNeedPayFitPenaltyRequestToCommand(fitPenaltyId, noNeedPayFitPenaltyRequest)
        val noNeedPayFitPenaltyResponseDto =
            updateFitPenaltyUseCase.noNeedPayFitPenaltyByFitReader(noNeedFitPenaltyCommand)
        return ResponseEntity.ok()
            .body(FitManagementDtoMapper.dtoToNoNeedPayFitPenaltyResponse(noNeedPayFitPenaltyResponseDto))
    }
}