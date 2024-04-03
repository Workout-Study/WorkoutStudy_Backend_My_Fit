package com.fitmate.myfit.adapter.`in`.event.mapper

import com.fitmate.myfit.application.port.`in`.fit.mate.command.SaveFitMateForReadCommand
import com.fitmate.myfit.common.exceptions.BadRequestException

class FitMateForReadMapper {

    companion object {
        fun saveFitMateRequestToCommand(fitGroupId: String, eventPublisher: String): SaveFitMateForReadCommand {
            val fitGroupIdLong: Long

            try {
                fitGroupIdLong = fitGroupId.toLong()
            } catch (exception: Exception) {
                throw BadRequestException("fit group must be long")
            }

            return SaveFitMateForReadCommand(
                fitGroupIdLong,
                eventPublisher
            )
        }
    }
}