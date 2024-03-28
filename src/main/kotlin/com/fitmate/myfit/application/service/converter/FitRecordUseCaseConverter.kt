package com.fitmate.myfit.application.service.converter

import com.fitmate.myfit.application.port.`in`.fit.record.response.RegisterFitRecordResponseDto
import com.fitmate.myfit.domain.FitRecord

class FitRecordUseCaseConverter private constructor() {

    companion object {
        fun fitRecordToRegisterResponseDto(fitRecord: FitRecord): RegisterFitRecordResponseDto {
            return RegisterFitRecordResponseDto(fitRecord.id != null)
        }
    }
}