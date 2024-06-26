package com.fitmate.myfit.adapter.out.persistence.repository

import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.application.port.`in`.fit.record.command.FitRecordFilterCommand
import com.fitmate.myfit.application.port.`in`.fit.record.command.FitRecordSliceFilterCommand

interface QFitRecordRepository {
    fun sliceByCommand(fitRecordSliceFilterCommand: FitRecordSliceFilterCommand): List<FitRecordEntity>

    fun filterByCommand(fitRecordFilterCommand: FitRecordFilterCommand): List<FitRecordEntity>
}