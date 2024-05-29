package com.fitmate.myfit.application.service.converter

import com.fitmate.myfit.application.port.`in`.fit.off.response.DeleteFitOffResponseDto
import com.fitmate.myfit.application.port.`in`.fit.off.response.RegisterFitOffResponseDto
import com.fitmate.myfit.application.port.`in`.fit.off.response.UpdateFitOffResponseDto
import com.fitmate.myfit.domain.FitOff

class FitOffUseCaseConverter {
    companion object {
        fun fitOffToRegisterResponseDto(fitOff: FitOff): RegisterFitOffResponseDto =
            RegisterFitOffResponseDto(fitOff.id != null, fitOff.id)

        fun resultToDeleteResponseDto(result: Boolean): DeleteFitOffResponseDto =
            DeleteFitOffResponseDto(result)

        fun resultToUpdateResponseDto(result: Boolean): UpdateFitOffResponseDto =
            UpdateFitOffResponseDto(result)
    }
}