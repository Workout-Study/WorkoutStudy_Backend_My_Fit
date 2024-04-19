package com.fitmate.myfit.application.port.`in`.fit.penalty.usecase

import com.fitmate.myfit.adapter.`in`.web.penalty.response.FitPenaltyFilteredResponse
import com.fitmate.myfit.application.port.`in`.fit.penalty.command.FitPenaltyFilterByUserCommand

interface ReadFitPenaltyUseCase {
    fun fitPenaltyFilterByUser(command: FitPenaltyFilterByUserCommand): FitPenaltyFilteredResponse
}