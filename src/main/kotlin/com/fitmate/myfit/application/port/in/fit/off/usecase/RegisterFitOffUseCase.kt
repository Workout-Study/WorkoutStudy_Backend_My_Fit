package com.fitmate.myfit.application.port.`in`.fit.off.usecase

import com.fitmate.myfit.application.port.`in`.fit.off.command.RegisterFitOffCommand
import com.fitmate.myfit.application.port.`in`.fit.off.response.RegisterFitOffResponseDto

interface RegisterFitOffUseCase {

    /**
     * Register fit off use case,
     * register fit off data to persistence
     *
     * @param registerFitOffCommand data about register fit off with user id
     * @return Boolean about register success
     */
    fun registerFitOff(registerFitOffCommand: RegisterFitOffCommand): RegisterFitOffResponseDto
}