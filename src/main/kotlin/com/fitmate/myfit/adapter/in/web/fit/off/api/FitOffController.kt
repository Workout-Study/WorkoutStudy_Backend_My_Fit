package com.fitmate.myfit.adapter.`in`.web.fit.off.api

import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.fit.off.mapper.FitOffDtoMapper
import com.fitmate.myfit.adapter.`in`.web.fit.off.request.DeleteFitOffRequest
import com.fitmate.myfit.adapter.`in`.web.fit.off.request.RegisterFitOffRequest
import com.fitmate.myfit.adapter.`in`.web.fit.off.request.UpdateFitOffRequest
import com.fitmate.myfit.adapter.`in`.web.fit.off.response.DeleteFitOffResponse
import com.fitmate.myfit.adapter.`in`.web.fit.off.response.RegisterFitOffResponse
import com.fitmate.myfit.adapter.`in`.web.fit.off.response.UpdateFitOffResponse
import com.fitmate.myfit.application.port.`in`.fit.off.usecase.DeleteFitOffUseCase
import com.fitmate.myfit.application.port.`in`.fit.off.usecase.RegisterFitOffUseCase
import com.fitmate.myfit.application.port.`in`.fit.off.usecase.UpdateFitOffUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class FitOffController(
    private val registerFitOffUseCase: RegisterFitOffUseCase,
    private val deleteFitOffUseCase: DeleteFitOffUseCase,
    private val updateFitOffUseCase: UpdateFitOffUseCase
) {

    /**
     * Register fit off inbound adapter
     *
     * @param registerFitOffRequest data about register fit off
     * @return Boolean about register success
     */
    @PostMapping(GlobalURI.FIT_OFF_ROOT)
    fun registerFitOff(
        @RequestBody @Valid registerFitOffRequest: RegisterFitOffRequest
    ): ResponseEntity<RegisterFitOffResponse> {
        val registerFitOffCommand = FitOffDtoMapper.registerRequestToCommand(registerFitOffRequest)
        val registerFitOffResponseDto = registerFitOffUseCase.registerFitOff(registerFitOffCommand)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(FitOffDtoMapper.dtoToRegisterResponse(registerFitOffResponseDto))
    }

    /**
     * Delete fit off inbound adapter
     *
     * @param fitOffId request delete fit off id
     * @param deleteFitOffRequest request user id
     * @return Boolean about delete success
     */
    @DeleteMapping(GlobalURI.FIT_OFF_ROOT + GlobalURI.PATH_VARIABLE_FIT_OFF_ID_WITH_BRACE)
    fun deleteFitOff(
        @PathVariable(GlobalURI.PATH_VARIABLE_FIT_OFF_ID) fitOffId: Long,
        @RequestBody @Valid deleteFitOffRequest: DeleteFitOffRequest
    ): ResponseEntity<DeleteFitOffResponse> {
        val deleteFitOffCommand = FitOffDtoMapper.deleteRequestToCommand(fitOffId, deleteFitOffRequest)
        val deleteFitOffResponseDto = deleteFitOffUseCase.deleteFitOff(deleteFitOffCommand)
        return ResponseEntity.ok()
            .body(FitOffDtoMapper.dtoToDeleteResponse(deleteFitOffResponseDto))
    }

    /**
     * Update fit off inbound adapter
     *
     * @param updateFitOffRequest request user id with target category and target id
     * @return Boolean about update success
     */
    @PutMapping(GlobalURI.FIT_OFF_ROOT + GlobalURI.PATH_VARIABLE_FIT_OFF_ID_WITH_BRACE)
    fun updateFitOff(
        @PathVariable(GlobalURI.PATH_VARIABLE_FIT_OFF_ID) fitOffId: Long,
        @RequestBody @Valid updateFitOffRequest: UpdateFitOffRequest
    ): ResponseEntity<UpdateFitOffResponse> {
        val updateFitOffCommand = FitOffDtoMapper.updateRequestToCommand(fitOffId, updateFitOffRequest)
        val updateFitOffResponseDto = updateFitOffUseCase.updateFitOff(updateFitOffCommand)
        return ResponseEntity.ok().body(FitOffDtoMapper.dtoToUpdateResponse(updateFitOffResponseDto))
    }
}