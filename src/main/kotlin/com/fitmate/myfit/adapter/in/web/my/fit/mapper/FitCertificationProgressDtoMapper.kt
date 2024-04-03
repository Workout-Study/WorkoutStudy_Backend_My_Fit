package com.fitmate.myfit.adapter.`in`.web.my.fit.mapper

import com.fitmate.myfit.adapter.`in`.web.my.fit.request.FitCertificationProgressFilterRequest
import com.fitmate.myfit.adapter.`in`.web.my.fit.response.FitCertificationProgressResponse
import com.fitmate.myfit.application.port.`in`.my.fit.command.FitCertificationProgressFilterCommand
import com.fitmate.myfit.application.port.`in`.my.fit.response.FilterCertificationProgressResponseDto

class FitCertificationProgressDtoMapper private constructor() {

    companion object {
        fun filterRequestToCommand(request: FitCertificationProgressFilterRequest): FitCertificationProgressFilterCommand =
            FitCertificationProgressFilterCommand(request.requestUserId)

        fun dtoToFilteredCertificationProgressResponse(
            dtoList: List<FilterCertificationProgressResponseDto>
        ): FitCertificationProgressResponse = FitCertificationProgressResponse(dtoList)
    }
}