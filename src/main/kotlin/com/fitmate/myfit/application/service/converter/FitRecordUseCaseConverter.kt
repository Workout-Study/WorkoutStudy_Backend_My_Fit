package com.fitmate.myfit.application.service.converter

import com.fitmate.myfit.application.port.`in`.fit.record.response.DeleteFitRecordResponseDto
import com.fitmate.myfit.application.port.`in`.fit.record.response.RegisterFitRecordResponseDto
import com.fitmate.myfit.application.port.`in`.fit.record.response.UpdateFitRecordMultiMediaEndPointResponseDto
import com.fitmate.myfit.domain.FitRecord

class FitRecordUseCaseConverter private constructor() {

    companion object {
        fun fitRecordToRegisterResponseDto(fitRecord: FitRecord): RegisterFitRecordResponseDto =
            RegisterFitRecordResponseDto(fitRecord.id != null, fitRecord.id)


        fun resultToDeleteResponseDto(result: Boolean): DeleteFitRecordResponseDto =
            DeleteFitRecordResponseDto(result)

        fun resultToUpdateMultiMediaEndPointResponseDto(result: Boolean): UpdateFitRecordMultiMediaEndPointResponseDto =
            UpdateFitRecordMultiMediaEndPointResponseDto(result)
    }
}