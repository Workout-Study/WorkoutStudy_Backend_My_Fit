package com.fitmate.myfit.adapter.`in`.web.certification.mapper

import com.fitmate.myfit.adapter.`in`.web.certification.response.FitCertificationDetailsResponse
import com.fitmate.myfit.application.port.`in`.certification.command.FitCertificationProgressByGroupIdCommand
import com.fitmate.myfit.application.port.`in`.certification.response.FitCertificationDetailWithVoteResponseDto
import com.fitmate.myfit.application.port.`in`.my.fit.command.FitCertificationFilterByGroupCommand

class FitCertificationFilterDtoMapper {

    companion object {
        fun filterByGroupIdAndUserIdRequestToCommand(
            fitGroupId: Long,
            requestUserId: String
        ): FitCertificationFilterByGroupCommand =
            FitCertificationFilterByGroupCommand(fitGroupId, requestUserId)

        fun dtoToFitCertificationDetailsResponse(dtoList: List<FitCertificationDetailWithVoteResponseDto>): FitCertificationDetailsResponse =
            FitCertificationDetailsResponse(dtoList)

        fun filterByGroupIdRequestToCommand(fitGroupId: Long): FitCertificationProgressByGroupIdCommand =
            FitCertificationProgressByGroupIdCommand(fitGroupId)
    }
}