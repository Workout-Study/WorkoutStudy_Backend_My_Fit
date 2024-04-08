package com.fitmate.myfit.adapter.`in`.event.mapper

import com.fitmate.myfit.application.port.`in`.fit.group.command.SaveFitGroupForReadCommand
import com.fitmate.myfit.common.exceptions.BadRequestException

class FitGroupForReadMapper {

    companion object {
        fun saveFitGroupRequestToCommand(fitGroupId: String, eventPublisher: String): SaveFitGroupForReadCommand {
            val fitGroupIdLong: Long

            try {
                fitGroupIdLong = fitGroupId.toLong()
            } catch (exception: Exception) {
                throw BadRequestException("fit group must be long")
            }

            return SaveFitGroupForReadCommand(
                fitGroupIdLong,
                eventPublisher
            )
        }
    }
}