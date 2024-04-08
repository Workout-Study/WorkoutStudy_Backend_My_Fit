package com.fitmate.myfit.application.port.`in`.my.fit.usecase

import com.fitmate.myfit.application.port.`in`.my.fit.command.FitCertificationProgressFilterCommand
import com.fitmate.myfit.application.port.`in`.my.fit.response.FilterCertificationProgressResponseDto

interface ReadFitCertificationProgressUseCase {

    /**
     * Get Filtered Fit certification progress use case
     *
     * @param command filter condition with user id
     * @return content list
     */
    fun filterFitCertificationProgress(
        command: FitCertificationProgressFilterCommand
    ): List<FilterCertificationProgressResponseDto>
}