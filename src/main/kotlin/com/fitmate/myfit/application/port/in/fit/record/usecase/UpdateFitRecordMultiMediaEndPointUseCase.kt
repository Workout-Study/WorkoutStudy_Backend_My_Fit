package com.fitmate.myfit.application.port.`in`.fit.record.usecase

import com.fitmate.myfit.application.port.`in`.fit.record.command.UpdateFitRecordMultiMediaEndPointCommand
import com.fitmate.myfit.application.port.`in`.fit.record.response.UpdateFitRecordMultiMediaEndPointResponseDto

interface UpdateFitRecordMultiMediaEndPointUseCase {

    fun updateFitRecordMultiMediaEndPoint(command: UpdateFitRecordMultiMediaEndPointCommand): UpdateFitRecordMultiMediaEndPointResponseDto
}