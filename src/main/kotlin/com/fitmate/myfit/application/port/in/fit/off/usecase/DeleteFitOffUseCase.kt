package com.fitmate.myfit.application.port.`in`.fit.off.usecase

import com.fitmate.myfit.application.port.`in`.fit.off.command.DeleteFitOffCommand
import com.fitmate.myfit.application.port.`in`.fit.off.response.DeleteFitOffResponseDto

interface DeleteFitOffUseCase {

    /**
     * Delete fit off use case,
     * delete fit off data to persistence
     *
     * @param deleteFitOffCommand data about delete fit off with user id
     * @return Boolean about delete success
     */
    fun deleteFitOff(deleteFitOffCommand: DeleteFitOffCommand): DeleteFitOffResponseDto
}