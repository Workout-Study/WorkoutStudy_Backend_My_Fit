package com.fitmate.myfit.application.port.`in`.fit.record.usecase

import com.fitmate.myfit.application.port.`in`.fit.record.command.DeleteFitRecordCommand
import com.fitmate.myfit.application.port.`in`.fit.record.response.DeleteFitRecordResponseDto

interface DeleteFitRecordUseCase {

    /**
     * Delete fit record use case,
     * delete fit record data to persistence
     *
     * @param deleteFitRecordCommand data about delete fit record with user id
     * @return Boolean about delete success
     */
    fun deleteFitRecord(deleteFitRecordCommand: DeleteFitRecordCommand): DeleteFitRecordResponseDto
}