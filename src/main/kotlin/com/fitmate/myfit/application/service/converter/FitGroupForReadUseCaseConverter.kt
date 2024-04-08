package com.fitmate.myfit.application.service.converter

import com.fitmate.myfit.application.port.`in`.fit.group.response.SaveFitGroupForReadResponseDto

class FitGroupForReadUseCaseConverter {

    companion object {
        fun resultToSaveResponseDto(result: Boolean): SaveFitGroupForReadResponseDto =
            SaveFitGroupForReadResponseDto(result)
    }
}