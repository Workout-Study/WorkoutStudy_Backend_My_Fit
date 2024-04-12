package com.fitmate.myfit.adapter.`in`.web.certification.mapper

import com.fitmate.myfit.adapter.`in`.web.certification.response.FitCertificationDetailsResponse
import com.fitmate.myfit.application.port.`in`.my.fit.command.FitCertificationFilterByGroupCommand
import com.fitmate.myfit.application.port.`in`.my.fit.response.FitCertificationDetailResponseDto

class FitCertificationFilterDtoMapper {

    companion object {
        fun filterByGroupIdRequestToCommand(
            fitGroupId: Long,
            requestUserId: String
        ): FitCertificationFilterByGroupCommand =
            FitCertificationFilterByGroupCommand(fitGroupId, requestUserId)

        fun dtoToFitCertificationDetailsResponse(dtoList: List<FitCertificationDetailResponseDto>): FitCertificationDetailsResponse =
            FitCertificationDetailsResponse(dtoList)
    }
}