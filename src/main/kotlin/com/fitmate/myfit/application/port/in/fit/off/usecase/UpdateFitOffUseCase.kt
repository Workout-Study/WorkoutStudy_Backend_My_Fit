package com.fitmate.myfit.application.port.`in`.fit.off.usecase

import com.fitmate.myfit.application.port.`in`.fit.off.command.UpdateFitOffCommand
import com.fitmate.myfit.application.port.`in`.fit.off.response.UpdateFitOffResponseDto

interface UpdateFitOffUseCase {

    /**
     * Update fit off use case,
     * update fit off data to persistence
     *
     * @param updateFitOffCommand data about update fit off with user id
     * @return Boolean about update success
     */
    fun updateFitOff(updateFitOffCommand: UpdateFitOffCommand): UpdateFitOffResponseDto
}