package com.fitmate.myfit.adapter.`in`.web.penalty.api

import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.penalty.mapper.FitPenaltyFilterDtoMapper
import com.fitmate.myfit.adapter.`in`.web.penalty.request.FitPenaltyFilterByFitGroupRequest
import com.fitmate.myfit.adapter.`in`.web.penalty.request.FitPenaltyFilterByUserRequest
import com.fitmate.myfit.adapter.`in`.web.penalty.response.FitPenaltyFilteredResponse
import com.fitmate.myfit.application.port.`in`.fit.penalty.usecase.ReadFitPenaltyUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class FitPenaltyFilterController(
    private val readFitPenaltyUseCase: ReadFitPenaltyUseCase
) {

    @GetMapping(GlobalURI.FIT_PENALTY_FILTER_BY_USER + GlobalURI.PATH_VARIABLE_USER_ID_WITH_BRACE)
    fun fitPenaltyFilterByUser(
        @PathVariable(GlobalURI.PATH_VARIABLE_USER_ID) userId: String,
        @ModelAttribute request: FitPenaltyFilterByUserRequest
    ): ResponseEntity<FitPenaltyFilteredResponse> {
        val fitPenaltyFilterByUserCommand = FitPenaltyFilterDtoMapper.filterByUserRequestToCommand(userId, request)
        return ResponseEntity.ok().body(readFitPenaltyUseCase.fitPenaltyFilterByUser(fitPenaltyFilterByUserCommand))
    }

    @GetMapping(GlobalURI.FIT_PENALTY_FILTER_BY_FIT_GROUP + GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE)
    fun fitPenaltyFilterByFitGroupId(
        @PathVariable(GlobalURI.PATH_VARIABLE_FIT_GROUP_ID) fitGroupId: Long,
        @ModelAttribute request: FitPenaltyFilterByFitGroupRequest
    ): ResponseEntity<FitPenaltyFilteredResponse> {
        val fitPenaltyFilterByFitGroupCommand =
            FitPenaltyFilterDtoMapper.filterByFitGroupRequestToCommand(fitGroupId, request)
        return ResponseEntity.ok()
            .body(readFitPenaltyUseCase.fitPenaltyFilterByFitGroupId(fitPenaltyFilterByFitGroupCommand))
    }
}