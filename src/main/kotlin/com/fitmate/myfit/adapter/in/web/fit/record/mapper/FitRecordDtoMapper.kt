package com.fitmate.myfit.adapter.`in`.web.fit.record.mapper

import com.fitmate.myfit.adapter.`in`.web.fit.record.request.FitRecordFilterRequest
import com.fitmate.myfit.adapter.`in`.web.fit.record.request.FitRecordSliceFilterRequest
import com.fitmate.myfit.adapter.`in`.web.fit.record.request.RegisterFitRecordRequest
import com.fitmate.myfit.adapter.`in`.web.fit.record.response.FitRecordDetailResponse
import com.fitmate.myfit.adapter.`in`.web.fit.record.response.RegisterFitRecordResponse
import com.fitmate.myfit.application.port.`in`.fit.record.command.FitRecordFilterCommand
import com.fitmate.myfit.application.port.`in`.fit.record.command.FitRecordSliceFilterCommand
import com.fitmate.myfit.application.port.`in`.fit.record.command.RegisterFitRecordCommand
import com.fitmate.myfit.application.port.`in`.fit.record.response.FitRecordDetailResponseDto
import com.fitmate.myfit.application.port.`in`.fit.record.response.RegisterFitRecordResponseDto
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice

class FitRecordDtoMapper private constructor() {

    companion object {
        fun registerRequestToCommand(request: RegisterFitRecordRequest): RegisterFitRecordCommand {
            return RegisterFitRecordCommand(
                request.requestUserId,
                request.recordStartDate,
                request.recordEndDate,
                request.multiMediaEndPoints
            )
        }

        fun dtoToRegisterResponse(registerFitRecordResponseDto: RegisterFitRecordResponseDto): RegisterFitRecordResponse {
            return RegisterFitRecordResponse(registerFitRecordResponseDto.isRegisterSuccess)
        }

        fun sliceFilterRequestToCommand(fitRecordSliceFilterRequest: FitRecordSliceFilterRequest): FitRecordSliceFilterCommand {
            val pageable = PageRequest.of(fitRecordSliceFilterRequest.pageNumber, fitRecordSliceFilterRequest.pageSize)

            return FitRecordSliceFilterCommand(
                fitRecordSliceFilterRequest.userId,
                fitRecordSliceFilterRequest.recordEndStartDate,
                fitRecordSliceFilterRequest.recordEndEndDate,
                pageable
            )
        }

        fun dtoToSliceFilteredRecordResponse(filterRecordResponseDtoSlice: Slice<FitRecordDetailResponseDto>): Slice<FitRecordDetailResponseDto> {
            return filterRecordResponseDtoSlice
        }

        fun filterRequestToCommand(fitRecordFilterRequest: FitRecordFilterRequest): FitRecordFilterCommand {
            return FitRecordFilterCommand(
                fitRecordFilterRequest.userId,
                fitRecordFilterRequest.recordEndStartDate,
                fitRecordFilterRequest.recordEndEndDate
            )
        }

        fun dtoToFilteredRecordResponse(filterRecordResponseDtoList: List<FitRecordDetailResponseDto>): FitRecordDetailResponse =
            FitRecordDetailResponse(filterRecordResponseDtoList)
    }
}