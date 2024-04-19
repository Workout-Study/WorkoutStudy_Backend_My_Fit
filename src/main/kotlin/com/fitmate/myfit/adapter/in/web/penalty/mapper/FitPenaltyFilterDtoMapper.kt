package com.fitmate.myfit.adapter.`in`.web.penalty.mapper

import com.fitmate.myfit.adapter.`in`.web.penalty.request.FitPenaltyFilterByUserRequest
import com.fitmate.myfit.application.port.`in`.fit.penalty.command.FitPenaltyFilterByUserCommand

class FitPenaltyFilterDtoMapper {
    companion object {
        fun filterByUserRequestToCommand(
            userId: String,
            request: FitPenaltyFilterByUserRequest
        ): FitPenaltyFilterByUserCommand =
            FitPenaltyFilterByUserCommand(
                userId,
                request.fitGroupId,
                request.startDate,
                request.endDate,
                request.onlyPaid,
                request.onlyNotPaid,
                request.pageNumber,
                request.pageSize
            )
    }
}