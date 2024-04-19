package com.fitmate.myfit.application.port.`in`.fit.penalty.usecase

import com.fitmate.myfit.application.port.`in`.fit.penalty.command.RegisterFitPenaltyCommand
import com.fitmate.myfit.application.port.`in`.fit.penalty.response.SaveFitPenaltyResponseDto

interface SaveFitPenaltyUseCase {
    fun saveFitPenalty(command: RegisterFitPenaltyCommand): SaveFitPenaltyResponseDto
}