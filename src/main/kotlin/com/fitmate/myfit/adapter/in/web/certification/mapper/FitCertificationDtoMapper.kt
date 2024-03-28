package com.fitmate.myfit.adapter.`in`.web.certification.mapper

import com.fitmate.myfit.adapter.`in`.web.certification.request.DeleteFitCertificationRequest
import com.fitmate.myfit.adapter.`in`.web.certification.request.RegisterFitCertificationRequest
import com.fitmate.myfit.adapter.`in`.web.certification.response.DeleteFitCertificationResponse
import com.fitmate.myfit.adapter.`in`.web.certification.response.RegisterFitCertificationResponse
import com.fitmate.myfit.application.port.`in`.certification.command.DeleteFitCertificationCommand
import com.fitmate.myfit.application.port.`in`.certification.command.RegisterFitCertificationCommand
import com.fitmate.myfit.application.port.`in`.certification.response.RegisterFitCertificationResponseDto
import com.fitmate.myfit.application.port.`in`.fit.record.response.DeleteFitCertificationResponseDto

class FitCertificationDtoMapper {

    companion object {
        fun registerRequestToCommand(request: RegisterFitCertificationRequest): RegisterFitCertificationCommand {
            return RegisterFitCertificationCommand(
                request.requestUserId,
                request.fitRecordId,
                request.fitGroupIds
            )
        }

        fun dtoToRegisterResponse(dto: RegisterFitCertificationResponseDto): RegisterFitCertificationResponse {
            return RegisterFitCertificationResponse(
                dto.isRegisterSuccess
            )
        }

        fun deleteRequestToCommand(
            fitCertificationId: Long,
            request: DeleteFitCertificationRequest
        ): DeleteFitCertificationCommand {
            return DeleteFitCertificationCommand(fitCertificationId, request.requestUserId)
        }

        fun dtoToDeleteResponse(dto: DeleteFitCertificationResponseDto): DeleteFitCertificationResponse {
            return DeleteFitCertificationResponse(dto.isDeleteSuccess)
        }
    }
}