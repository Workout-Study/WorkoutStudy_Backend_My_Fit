package com.fitmate.myfit.adapter.`in`.web.my.fit.api

import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.my.fit.mapper.FitCertificationProgressDtoMapper
import com.fitmate.myfit.adapter.`in`.web.my.fit.request.FitCertificationProgressFilterRequest
import com.fitmate.myfit.adapter.`in`.web.my.fit.response.FitCertificationProgressResponse
import com.fitmate.myfit.application.port.`in`.my.fit.usecase.ReadFitCertificationProgressUseCase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController

@RestController
class MyFitController(private val readFitCertificationProgressUseCase: ReadFitCertificationProgressUseCase) {

    /**
     * Get Filtered Fit certification progress inbound adapter
     *
     * @param fitCertificationProgressFilterRequest filter condition with user id
     * @return content list
     */
    @GetMapping(GlobalURI.MY_FIT_CERTIFICATION_PROGRESS)
    fun getFitCertificationProgress(
        @ModelAttribute @Valid fitCertificationProgressFilterRequest: FitCertificationProgressFilterRequest
    ): ResponseEntity<FitCertificationProgressResponse> {
        val fitCertificationProgressFilterCommand =
            FitCertificationProgressDtoMapper.filterRequestToCommand(fitCertificationProgressFilterRequest)
        val filterCertificationProgressResponseDtoList =
            readFitCertificationProgressUseCase.filterFitCertificationProgress(fitCertificationProgressFilterCommand)
        return ResponseEntity.ok().body(
            FitCertificationProgressDtoMapper.dtoToFilteredCertificationProgressResponse(
                filterCertificationProgressResponseDtoList
            )
        )
    }
}