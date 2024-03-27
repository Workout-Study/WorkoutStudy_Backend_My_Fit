package com.fitmate.myfit.adapter.`in`.web.fit.record.api

import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.fit.record.mapper.FitRecordDtoMapper
import com.fitmate.myfit.adapter.`in`.web.fit.record.request.FitRecordFilterRequest
import com.fitmate.myfit.adapter.`in`.web.fit.record.request.FitRecordSliceFilterRequest
import com.fitmate.myfit.adapter.`in`.web.fit.record.response.FitRecordDetailResponse
import com.fitmate.myfit.application.port.`in`.fit.record.response.FitRecordDetailResponseDto
import com.fitmate.myfit.application.port.`in`.fit.record.usecase.ReadFitRecordUseCase
import jakarta.validation.Valid
import org.springframework.data.domain.Slice
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController

@RestController
class FitRecordFilterController(
    private val readFitRecordUseCase: ReadFitRecordUseCase
) {

    /**
     * Get Filtered Fit record inbound adapter
     *
     * @param fitRecordFilterRequest filter condition with user id
     * @return content and list data
     */
    @GetMapping(GlobalURI.FIT_RECORD_FILTER)
    fun getFitRecordListByFilter(@ModelAttribute @Valid fitRecordFilterRequest: FitRecordFilterRequest): ResponseEntity<FitRecordDetailResponse> {
        val fitRecordFilterCommand = FitRecordDtoMapper.filterRequestToCommand(fitRecordFilterRequest)
        val filterRecordResponseDtoList = readFitRecordUseCase.filterFitRecord(fitRecordFilterCommand)
        return ResponseEntity.ok()
            .body(FitRecordDtoMapper.dtoToFilteredRecordResponse(filterRecordResponseDtoList))
    }

    /**
     * Get Filtered Sliced Fit record inbound adapter
     *
     * @param fitRecordSliceFilterRequest filter condition with user id
     * @return content and slice data
     */
    @GetMapping(GlobalURI.FIT_RECORD_SLICE_FILTER)
    fun getFitRecordListBySliceFilter(@ModelAttribute @Valid fitRecordSliceFilterRequest: FitRecordSliceFilterRequest): ResponseEntity<Slice<FitRecordDetailResponseDto>> {
        val fitRecordSliceFilterCommand = FitRecordDtoMapper.sliceFilterRequestToCommand(fitRecordSliceFilterRequest)
        val sliceFilterRecordResponseDto = readFitRecordUseCase.sliceFilterFitRecord(fitRecordSliceFilterCommand)
        return ResponseEntity.ok().body(sliceFilterRecordResponseDto)
    }
}