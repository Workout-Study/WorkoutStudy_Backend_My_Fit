package com.fitmate.myfit.application.service.converter

import com.fitmate.myfit.application.port.`in`.fit.penalty.response.SaveFitPenaltyResponseDto

class FitPenaltyUseCaseConverter {
    companion object {
        fun resultToSaveResponseDto(result: Boolean): SaveFitPenaltyResponseDto =
            SaveFitPenaltyResponseDto(result)
    }
}