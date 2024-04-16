package com.fitmate.myfit.adapter.`in`.web.certification.api

import com.fitmate.myfit.adapter.`in`.web.certification.mapper.FitCertificationDtoMapper
import com.fitmate.myfit.adapter.`in`.web.certification.request.DeleteFitCertificationRequest
import com.fitmate.myfit.adapter.`in`.web.certification.request.RegisterFitCertificationRequest
import com.fitmate.myfit.adapter.`in`.web.certification.response.DeleteFitCertificationResponse
import com.fitmate.myfit.adapter.`in`.web.certification.response.FitCertificationDetailProgressResponse
import com.fitmate.myfit.adapter.`in`.web.certification.response.FitCertificationDetailResponse
import com.fitmate.myfit.adapter.`in`.web.certification.response.RegisterFitCertificationResponse
import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.application.port.`in`.certification.usecase.DeleteFitCertificationUseCase
import com.fitmate.myfit.application.port.`in`.certification.usecase.ReadFitCertificationUseCase
import com.fitmate.myfit.application.port.`in`.certification.usecase.RegisterFitCertificationUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class FitCertificationController(
    private val registerFitCertificationUseCase: RegisterFitCertificationUseCase,
    private val deleteFitCertificationUseCase: DeleteFitCertificationUseCase,
    private val readFitCertificationUseCase: ReadFitCertificationUseCase
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

    /**
     * Delete fit certification inbound adapter
     *
     * @param fitCertificationId request delete fit certification id
     * @param deleteFitCertificationRequest request user id
     * @return Boolean about delete success
     */
    @DeleteMapping(GlobalURI.FIT_CERTIFICATION_ROOT + GlobalURI.PATH_VARIABLE_FIT_CERTIFICATION_ID_WITH_BRACE)
    fun deleteFitCertification(
        @PathVariable(GlobalURI.PATH_VARIABLE_FIT_CERTIFICATION_ID) fitCertificationId: Long,
        @RequestBody @Valid deleteFitCertificationRequest: DeleteFitCertificationRequest
    ): ResponseEntity<DeleteFitCertificationResponse> {
        val deleteFitCertificationCommand =
            FitCertificationDtoMapper.deleteRequestToCommand(fitCertificationId, deleteFitCertificationRequest)
        val deleteFitCertificationResponseDto =
            deleteFitCertificationUseCase.deleteFitCertification(deleteFitCertificationCommand)
        return ResponseEntity.ok()
            .body(FitCertificationDtoMapper.dtoToDeleteResponse(deleteFitCertificationResponseDto))
    }

    /**
     * Get fit certification detail inbound adapter
     */
    @GetMapping(GlobalURI.FIT_CERTIFICATION_ROOT + GlobalURI.PATH_VARIABLE_FIT_CERTIFICATION_ID_WITH_BRACE)
    fun getFitCertificationDetail(
        @PathVariable(GlobalURI.PATH_VARIABLE_FIT_CERTIFICATION_ID) fitCertificationId: Long
    ): ResponseEntity<FitCertificationDetailResponse> {
        val fitCertificationDetailCommand =
            FitCertificationDtoMapper.detailRequestToCommand(fitCertificationId)
        val fitCertificationDetailResponseDto =
            readFitCertificationUseCase.getFitCertificationDetail(fitCertificationDetailCommand)
        return ResponseEntity.ok()
            .body(FitCertificationDtoMapper.dtoToDetailResponse(fitCertificationDetailResponseDto))
    }

    /**
     * Get fit certification detail with vote inbound adapter
     */
    @GetMapping(GlobalURI.FIT_CERTIFICATION_PROGRESS + GlobalURI.PATH_VARIABLE_FIT_CERTIFICATION_ID_WITH_BRACE)
    fun getFitCertificationDetailProgress(
        @PathVariable(GlobalURI.PATH_VARIABLE_FIT_CERTIFICATION_ID) fitCertificationId: Long
    ): ResponseEntity<FitCertificationDetailProgressResponse> {
        val fitCertificationDetailProgressCommand =
            FitCertificationDtoMapper.detailProgressRequestToCommand(fitCertificationId)
        val fitCertificationDetailProgressResponseDto =
            readFitCertificationUseCase.getFitCertificationDetailProgress(fitCertificationDetailProgressCommand)
        return ResponseEntity.ok()
            .body(FitCertificationDtoMapper.dtoToDetailProgressResponse(fitCertificationDetailProgressResponseDto))
    }
}