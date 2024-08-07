package com.fitmate.myfit.adapter.`in`.web.certification.api

import com.fitmate.myfit.adapter.`in`.web.certification.mapper.FitCertificationFilterDtoMapper
import com.fitmate.myfit.adapter.`in`.web.certification.response.FitCertificationDetailsResponse
import com.fitmate.myfit.adapter.`in`.web.certification.response.FitCertificationProgressesResponse
import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.application.port.`in`.certification.usecase.ReadFitCertificationUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class FitCertificationFilterController(private val readFitCertificationUseCase: ReadFitCertificationUseCase) {

    @GetMapping(
        GlobalURI.FIT_CERTIFICATION_FILTER
                + GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE
                + GlobalURI.PATH_VARIABLE_USER_ID_WITH_BRACE
    )
    fun getFitCertificationByGroupId(
        @PathVariable(GlobalURI.PATH_VARIABLE_FIT_GROUP_ID) fitGroupId: Long,
        @PathVariable(GlobalURI.PATH_VARIABLE_USER_ID) requestUserId: Int,
        @RequestParam(required = false) withOwn: Int?
    ): ResponseEntity<FitCertificationDetailsResponse> {
        val fitCertificationFilterByGroupIdCommand =
            FitCertificationFilterDtoMapper.filterByGroupIdAndUserIdRequestToCommand(fitGroupId, requestUserId, withOwn)
        val fitCertificationDetailResponseDtoList =
            readFitCertificationUseCase.getFitCertificationByGroupId(fitCertificationFilterByGroupIdCommand)
        return ResponseEntity.ok()
            .body(
                FitCertificationFilterDtoMapper.dtoToFitCertificationDetailsResponse(
                    fitCertificationDetailResponseDtoList
                )
            )
    }

    @GetMapping(
        GlobalURI.MY_FIT_CERTIFICATION_PROGRESS
                + GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE
    )
    fun getFitCertificationProgressByGroupId(
        @PathVariable(GlobalURI.PATH_VARIABLE_FIT_GROUP_ID) fitGroupId: Long,
    ): ResponseEntity<FitCertificationProgressesResponse> {
        val fitCertificationProgressByGroupIdCommand =
            FitCertificationFilterDtoMapper.filterByGroupIdRequestToCommand(fitGroupId)
        val fitCertificationProgressResponseDtoList =
            readFitCertificationUseCase.getFitCertificationProgressByGroupId(fitCertificationProgressByGroupIdCommand)
        return ResponseEntity.ok().body(fitCertificationProgressResponseDtoList)
    }
}