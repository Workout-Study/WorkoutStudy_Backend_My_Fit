package com.fitmate.myfit.application.port.out

import com.fitmate.myfit.application.port.`in`.command.FitRecordFilterCommand
import com.fitmate.myfit.application.port.`in`.command.FitRecordSliceFilterCommand
import com.fitmate.myfit.domain.FitRecord

interface ReadFitRecordPort {
    fun sliceFilterFitRecord(fitRecordSliceFilterCommand: FitRecordSliceFilterCommand): List<FitRecord>

    fun filterFitRecord(fitRecordFilterCommand: FitRecordFilterCommand): List<FitRecord>
}