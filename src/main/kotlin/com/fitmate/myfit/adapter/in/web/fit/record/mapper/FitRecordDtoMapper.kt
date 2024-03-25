package com.fitmate.myfit.adapter.`in`.web.fit.record.mapper

import com.fitmate.myfit.adapter.`in`.web.fit.record.request.RegisterFitRecordRequest
import com.fitmate.myfit.adapter.`in`.web.fit.record.response.RegisterFitRecordResponse
import com.fitmate.myfit.application.port.`in`.command.RegisterFitRecordCommand
import com.fitmate.myfit.application.port.`in`.response.RegisterFitRecordResponseDto

class FitRecordDtoMapper private constructor() {

    companion object {
        fun registerRequestToCommand(request: RegisterFitRecordRequest): RegisterFitRecordCommand {
            return RegisterFitRecordCommand(
                request.requestUserId,
                request.recordStartDate,
                request.recordEndDate,
                request.multiMediaEndPoints
            )
        }

        fun dtoToRegisterResponse(registerFitRecordResponseDto: RegisterFitRecordResponseDto): RegisterFitRecordResponse {
            return RegisterFitRecordResponse(registerFitRecordResponseDto.isRegisterSuccess)
        }
    }
}