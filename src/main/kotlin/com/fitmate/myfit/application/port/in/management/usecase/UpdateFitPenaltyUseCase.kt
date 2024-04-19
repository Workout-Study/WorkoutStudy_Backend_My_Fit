package com.fitmate.myfit.application.port.`in`.management.usecase

import com.fitmate.myfit.application.port.`in`.management.command.NoNeedPayFitPenaltyCommand
import com.fitmate.myfit.application.port.`in`.management.command.PaidFitPenaltyCommand
import com.fitmate.myfit.application.port.`in`.management.response.NoNeedPayFitPenaltyResponseDto
import com.fitmate.myfit.application.port.`in`.management.response.PaidFitPenaltyResponseDto

interface UpdateFitPenaltyUseCase {
    fun paidFitPenaltyByFitReader(command: PaidFitPenaltyCommand): PaidFitPenaltyResponseDto
    fun noNeedPayFitPenaltyByFitReader(command: NoNeedPayFitPenaltyCommand): NoNeedPayFitPenaltyResponseDto
}