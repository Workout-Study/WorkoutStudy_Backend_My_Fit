package com.fitmate.myfit.application.port.`in`.fit.mate.usecase

import com.fitmate.myfit.application.port.`in`.fit.mate.command.SaveFitMateForReadCommand
import com.fitmate.myfit.application.port.`in`.fit.mate.response.SaveFitMateForReadResponseDto

interface SaveFitMateUseCase {

    /**
     * register fit mate data if not exist.
     * if exist update fit mate data
     *
     * @param saveFitMateForReadCommand data about fit group
     */
    fun saveFitMateForRead(saveFitMateForReadCommand: SaveFitMateForReadCommand): SaveFitMateForReadResponseDto
}