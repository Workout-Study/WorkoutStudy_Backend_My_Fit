package com.fitmate.myfit.adapter.`in`.event.mapper

import com.fitmate.myfit.adapter.`in`.event.dto.FitGroupDto
import com.fitmate.myfit.application.port.`in`.fit.group.command.SaveFitGroupForReadCommand

class FitGroupForReadMapper {

    companion object {
        fun saveDtoToCommand(dto: FitGroupDto, eventPublisher: String): SaveFitGroupForReadCommand {
            return SaveFitGroupForReadCommand(
                dto.fitGroupId,
                dto.fitGroupName,
                dto.cycle,
                dto.frequency,
                dto.state,
                eventPublisher
            )
        }
    }
}