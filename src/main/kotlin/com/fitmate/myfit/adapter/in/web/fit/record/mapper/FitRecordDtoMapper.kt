package com.fitmate.myfit.adapter.`in`.web.fit.record.mapper

import com.fitmate.myfit.adapter.`in`.web.fit.record.request.*
import com.fitmate.myfit.adapter.`in`.web.fit.record.response.DeleteFitRecordResponse
import com.fitmate.myfit.adapter.`in`.web.fit.record.response.FitRecordDetailResponse
import com.fitmate.myfit.adapter.`in`.web.fit.record.response.RegisterFitRecordResponse
import com.fitmate.myfit.adapter.`in`.web.fit.record.response.UpdateFitRecordMultiMediaEndPointResponse
import com.fitmate.myfit.application.port.`in`.fit.record.command.*
import com.fitmate.myfit.application.port.`in`.fit.record.response.DeleteFitRecordResponseDto
import com.fitmate.myfit.application.port.`in`.fit.record.response.FitRecordDetailResponseDto
import com.fitmate.myfit.application.port.`in`.fit.record.response.RegisterFitRecordResponseDto
import com.fitmate.myfit.application.port.`in`.fit.record.response.UpdateFitRecordMultiMediaEndPointResponseDto
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
            return RegisterFitRecordResponse(
                registerFitRecordResponseDto.isRegisterSuccess,
                registerFitRecordResponseDto.fitRecordId
            )
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

        fun deleteRequestToCommand(
            fitRecordId: Long,
            request: DeleteFitRecordRequest
        ): DeleteFitRecordCommand =
            DeleteFitRecordCommand(fitRecordId, request.requestUserId)

        fun dtoToDeleteResponse(dto: DeleteFitRecordResponseDto): DeleteFitRecordResponse =
            DeleteFitRecordResponse(dto.isDeleteSuccess)

        fun updateMultiMediaEndPointRequestToCommand(
            fitRecordId: Long,
            request: UpdateFitRecordMultiMediaEndPointRequest
        ) = UpdateFitRecordMultiMediaEndPointCommand(fitRecordId, request.requestUserId, request.multiMediaEndPoints)

        fun dtoToUpdateMultiMediaEndPointResponse(
            responseDto: UpdateFitRecordMultiMediaEndPointResponseDto
        ) = UpdateFitRecordMultiMediaEndPointResponse(responseDto.isUpdateSuccess)
    }
}