package com.fitmate.myfit.adapter.`in`.event.mapper

import com.fitmate.myfit.application.port.`in`.fit.penalty.command.RegisterFitPenaltyCommand
import org.apache.coyote.BadRequestException

class FitPenaltyDtoMapper {
    companion object {
        fun saveFitPenaltyRequestToCommand(fitPenaltyId: String, eventPublisher: String): RegisterFitPenaltyCommand {
            val fitPenaltyIdLong: Long

            try {
                fitPenaltyIdLong = fitPenaltyId.toLong()
            } catch (exception: Exception) {
                throw BadRequestException("fit penalty id must be long")
            }
            return RegisterFitPenaltyCommand(fitPenaltyIdLong, eventPublisher)
        }
    }
}