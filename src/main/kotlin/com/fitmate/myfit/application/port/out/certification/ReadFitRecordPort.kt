package com.fitmate.myfit.application.port.out.certification

import com.fitmate.myfit.application.port.`in`.fit.record.command.FitRecordFilterCommand
import com.fitmate.myfit.application.port.`in`.fit.record.command.FitRecordSliceFilterCommand
import com.fitmate.myfit.domain.FitRecord
import java.util.*

interface ReadFitRecordPort {
    fun sliceFilterFitRecord(fitRecordSliceFilterCommand: FitRecordSliceFilterCommand): List<FitRecord>

    fun filterFitRecord(fitRecordFilterCommand: FitRecordFilterCommand): List<FitRecord>

    fun findById(fitRecordId: Long): Optional<FitRecord>
}