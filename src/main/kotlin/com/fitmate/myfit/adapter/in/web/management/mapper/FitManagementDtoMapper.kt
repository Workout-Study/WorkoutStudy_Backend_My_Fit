package com.fitmate.myfit.adapter.`in`.web.management.mapper

import com.fitmate.myfit.adapter.`in`.web.management.request.NoNeedPayFitPenaltyRequest
import com.fitmate.myfit.adapter.`in`.web.management.request.PaidFitPenaltyRequest
import com.fitmate.myfit.adapter.`in`.web.management.response.NoNeedPayFitPenaltyResponse
import com.fitmate.myfit.adapter.`in`.web.management.response.PaidFitPenaltyResponse
import com.fitmate.myfit.application.port.`in`.management.command.NoNeedPayFitPenaltyCommand
import com.fitmate.myfit.application.port.`in`.management.command.PaidFitPenaltyCommand
import com.fitmate.myfit.application.port.`in`.management.response.NoNeedPayFitPenaltyResponseDto
import com.fitmate.myfit.application.port.`in`.management.response.PaidFitPenaltyResponseDto

class FitManagementDtoMapper {
    companion object {
        fun paidFitPenaltyRequestToCommand(fitPenaltyId: Long, request: PaidFitPenaltyRequest): PaidFitPenaltyCommand =
            PaidFitPenaltyCommand(fitPenaltyId, request.requestUserId)

        fun dtoToPaidFitPenaltyResponse(dto: PaidFitPenaltyResponseDto): PaidFitPenaltyResponse =
            PaidFitPenaltyResponse(dto.isPaidSuccess)

        fun noNeedPayFitPenaltyRequestToCommand(
            fitPenaltyId: Long,
            dto: NoNeedPayFitPenaltyRequest
        ): NoNeedPayFitPenaltyCommand =
            NoNeedPayFitPenaltyCommand(fitPenaltyId, dto.requestUserId)

        fun dtoToNoNeedPayFitPenaltyResponse(
            dto: NoNeedPayFitPenaltyResponseDto
        ): NoNeedPayFitPenaltyResponse =
            NoNeedPayFitPenaltyResponse(dto.isNoNeedPaySuccess)
    }
}