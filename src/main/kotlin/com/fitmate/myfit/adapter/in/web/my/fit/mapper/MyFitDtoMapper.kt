package com.fitmate.myfit.adapter.`in`.web.my.fit.mapper

import com.fitmate.myfit.adapter.`in`.web.my.fit.request.FitCertificationProgressFilterRequest
import com.fitmate.myfit.adapter.`in`.web.my.fit.request.MyFitGroupIssueSliceFilterRequest
import com.fitmate.myfit.adapter.`in`.web.my.fit.request.NeedVoteCertificationFilterRequest
import com.fitmate.myfit.adapter.`in`.web.my.fit.response.FitCertificationProgressResponse
import com.fitmate.myfit.adapter.`in`.web.my.fit.response.NeedVoteCertificationFilterResponse
import com.fitmate.myfit.application.port.`in`.my.fit.command.FitCertificationProgressFilterCommand
import com.fitmate.myfit.application.port.`in`.my.fit.command.MyFitGroupIssueSliceFilterCommand
import com.fitmate.myfit.application.port.`in`.my.fit.command.NeedVoteCertificationFilterCommand
import com.fitmate.myfit.application.port.`in`.my.fit.response.FilterCertificationProgressResponseDto
import com.fitmate.myfit.application.port.`in`.my.fit.response.NeedVoteCertificationFitGroupResponseDto
import org.springframework.data.domain.PageRequest

class MyFitDtoMapper private constructor() {

    companion object {
        fun filterRequestToCommand(request: FitCertificationProgressFilterRequest): FitCertificationProgressFilterCommand =
            FitCertificationProgressFilterCommand(request.requestUserId)

        fun dtoToFilteredCertificationProgressResponse(
            dtoList: List<FilterCertificationProgressResponseDto>
        ): FitCertificationProgressResponse = FitCertificationProgressResponse(dtoList)

        fun needVoteCertificationRequestToCommand(request: NeedVoteCertificationFilterRequest): NeedVoteCertificationFilterCommand =
            NeedVoteCertificationFilterCommand(request.requestUserId, request.fitGroupId)

        fun dtoToFilteredNeedVoteCertificationResponse(
            dtoList: List<NeedVoteCertificationFitGroupResponseDto>
        ): NeedVoteCertificationFilterResponse =
            NeedVoteCertificationFilterResponse(dtoList)

        fun myFitGroupIssueSliceFilterRequestToCommand(request: MyFitGroupIssueSliceFilterRequest): MyFitGroupIssueSliceFilterCommand {
            val pageable = PageRequest.of(request.pageNumber, request.pageSize)

            return MyFitGroupIssueSliceFilterCommand(
                request.userId,
                pageable
            )
        }
    }
}