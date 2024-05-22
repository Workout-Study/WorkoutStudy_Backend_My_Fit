package com.fitmate.myfit.adapter.`in`.web.penalty.mapper

import com.fitmate.myfit.adapter.`in`.web.penalty.request.FitPenaltyFilterByFitGroupRequest
import com.fitmate.myfit.adapter.`in`.web.penalty.request.FitPenaltyFilterByUserRequest
import com.fitmate.myfit.application.port.`in`.fit.penalty.command.FitPenaltyFilterByFitGroupCommand
import com.fitmate.myfit.application.port.`in`.fit.penalty.command.FitPenaltyFilterByUserCommand

class FitPenaltyFilterDtoMapper {
    companion object {
        fun filterByUserRequestToCommand(
            userId: Int,
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

        fun filterByFitGroupRequestToCommand(
            fitGroupId: Long,
            request: FitPenaltyFilterByFitGroupRequest
        ): FitPenaltyFilterByFitGroupCommand =
            FitPenaltyFilterByFitGroupCommand(
                request.fitMateUserId,
                fitGroupId,
                request.startDate,
                request.endDate,
                request.onlyPaid,
                request.onlyNotPaid,
                request.pageNumber,
                request.pageSize
            )
    }
}