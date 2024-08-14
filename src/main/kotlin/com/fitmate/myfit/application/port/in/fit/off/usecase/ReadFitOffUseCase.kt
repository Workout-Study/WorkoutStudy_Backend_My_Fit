package com.fitmate.myfit.application.port.`in`.fit.off.usecase

import com.fitmate.myfit.adapter.`in`.web.fit.off.response.ProceedingFitOffResponse
import com.fitmate.myfit.application.port.`in`.fit.off.command.GetProceedingFitOffCommand
import com.fitmate.myfit.application.port.`in`.fit.off.command.GetProceedingFitOffUserCommand

interface ReadFitOffUseCase {

    fun getProceedingFitOffByGroupId(command: GetProceedingFitOffCommand): ProceedingFitOffResponse
    fun getProceedingFitOffByUser(command: GetProceedingFitOffUserCommand): ProceedingFitOffResponse
}