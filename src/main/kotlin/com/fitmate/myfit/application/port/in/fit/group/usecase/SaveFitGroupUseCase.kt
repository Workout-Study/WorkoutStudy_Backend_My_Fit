package com.fitmate.myfit.application.port.`in`.fit.group.usecase

import com.fitmate.myfit.application.port.`in`.fit.group.command.SaveFitGroupForReadCommand
import com.fitmate.myfit.application.port.`in`.fit.group.response.SaveFitGroupForReadResponseDto

interface SaveFitGroupUseCase {

    /**
     * register fit group data if not exist.
     * if exist update fit group data
     *
     * @param saveFitGroupForReadCommand data about fit group
     */
    fun saveFitGroupForRead(saveFitGroupForReadCommand: SaveFitGroupForReadCommand): SaveFitGroupForReadResponseDto
}