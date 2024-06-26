package com.fitmate.myfit.application.port.`in`.fit.record.usecase

import com.fitmate.myfit.application.port.`in`.fit.record.command.FitRecordFilterCommand
import com.fitmate.myfit.application.port.`in`.fit.record.command.FitRecordSliceFilterCommand
import com.fitmate.myfit.application.port.`in`.fit.record.response.FitRecordDetailResponseDto
import org.springframework.data.domain.Slice

interface ReadFitRecordUseCase {

    /**
     * Get Filtered fit record list use case,
     * Get Filtered fit record list data
     *
     * @param fitRecordFilterCommand filter condition
     * @return content and list data
     */
    fun filterFitRecord(fitRecordFilterCommand: FitRecordFilterCommand): List<FitRecordDetailResponseDto>

    /**
     * Get Filtered Sliced fit record use case,
     * Get Filtered slice fit record data
     *
     * @param fitRecordSliceFilterCommand filter condition and pageable
     * @return content and slice data
     */
    fun sliceFilterFitRecord(fitRecordSliceFilterCommand: FitRecordSliceFilterCommand): Slice<FitRecordDetailResponseDto>
}