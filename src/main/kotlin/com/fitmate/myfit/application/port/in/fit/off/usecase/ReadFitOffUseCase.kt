package com.fitmate.myfit.application.port.`in`.fit.off.usecase

import com.fitmate.myfit.adapter.`in`.web.fit.off.response.ProceedingFitOffResponse
import com.fitmate.myfit.application.port.`in`.fit.off.command.GetProceedingFitOffCommand

interface ReadFitOffUseCase {

    fun getProceedingFitOffByGroupId(command: GetProceedingFitOffCommand): ProceedingFitOffResponse
}