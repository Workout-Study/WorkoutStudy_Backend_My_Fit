package com.fitmate.myfit.adapter.`in`.web.fit.off.mapper

import com.fitmate.myfit.adapter.`in`.web.fit.off.request.DeleteFitOffRequest
import com.fitmate.myfit.adapter.`in`.web.fit.off.request.RegisterFitOffRequest
import com.fitmate.myfit.adapter.`in`.web.fit.off.request.UpdateFitOffRequest
import com.fitmate.myfit.adapter.`in`.web.fit.off.response.DeleteFitOffResponse
import com.fitmate.myfit.adapter.`in`.web.fit.off.response.RegisterFitOffResponse
import com.fitmate.myfit.adapter.`in`.web.fit.off.response.UpdateFitOffResponse
import com.fitmate.myfit.adapter.out.api.DateParseUtils
import com.fitmate.myfit.application.port.`in`.fit.off.command.DeleteFitOffCommand
import com.fitmate.myfit.application.port.`in`.fit.off.command.GetProceedingFitOffCommand
import com.fitmate.myfit.application.port.`in`.fit.off.command.RegisterFitOffCommand
import com.fitmate.myfit.application.port.`in`.fit.off.command.UpdateFitOffCommand
import com.fitmate.myfit.application.port.`in`.fit.off.response.DeleteFitOffResponseDto
import com.fitmate.myfit.application.port.`in`.fit.off.response.RegisterFitOffResponseDto
import com.fitmate.myfit.application.port.`in`.fit.off.response.UpdateFitOffResponseDto
import java.time.Instant

class FitOffDtoMapper {

    companion object {
        fun registerRequestToCommand(request: RegisterFitOffRequest): RegisterFitOffCommand {
            val fitOffStartDate: Instant = DateParseUtils.stringToInstant(request.fitOffStartDate)
            val fitOffEndDate: Instant = DateParseUtils.stringToInstant(request.fitOffEndDate)

            return RegisterFitOffCommand(
                request.requestUserId,
                fitOffStartDate,
                fitOffEndDate,
                request.fitOffReason
            )
        }

        fun dtoToRegisterResponse(responseDto: RegisterFitOffResponseDto): RegisterFitOffResponse =
            RegisterFitOffResponse(responseDto.isRegisterSuccess, responseDto.fitOffId)

        fun deleteRequestToCommand(fitOffId: Long, request: DeleteFitOffRequest): DeleteFitOffCommand =
            DeleteFitOffCommand(fitOffId, request.requestUserId)

        fun dtoToDeleteResponse(responseDto: DeleteFitOffResponseDto): DeleteFitOffResponse =
            DeleteFitOffResponse(responseDto.isDeleteSuccess)

        fun updateRequestToCommand(fitOffId: Long, request: UpdateFitOffRequest): UpdateFitOffCommand {
            val fitOffStartDate: Instant = DateParseUtils.stringToInstant(request.fitOffStartDate)
            val fitOffEndDate: Instant = DateParseUtils.stringToInstant(request.fitOffEndDate)

            return UpdateFitOffCommand(
                fitOffId,
                request.requestUserId,
                fitOffStartDate,
                fitOffEndDate,
                request.fitOffReason
            )
        }

        fun dtoToUpdateResponse(updateFitOffResponseDto: UpdateFitOffResponseDto): UpdateFitOffResponse =
            UpdateFitOffResponse(updateFitOffResponseDto.isUpdateSuccess)

        fun proceedingFitOffRequestToCommand(fitGroupId: Long): GetProceedingFitOffCommand {
            return GetProceedingFitOffCommand(fitGroupId)
        }
    }
}