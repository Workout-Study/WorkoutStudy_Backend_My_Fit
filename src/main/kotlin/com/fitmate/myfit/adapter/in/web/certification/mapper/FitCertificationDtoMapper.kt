package com.fitmate.myfit.adapter.`in`.web.certification.mapper

import com.fitmate.myfit.adapter.`in`.web.certification.request.DeleteFitCertificationRequest
import com.fitmate.myfit.adapter.`in`.web.certification.request.RegisterFitCertificationRequest
import com.fitmate.myfit.adapter.`in`.web.certification.response.DeleteFitCertificationResponse
import com.fitmate.myfit.adapter.`in`.web.certification.response.FitCertificationDetailProgressResponse
import com.fitmate.myfit.adapter.`in`.web.certification.response.FitCertificationDetailResponse
import com.fitmate.myfit.adapter.`in`.web.certification.response.RegisterFitCertificationResponse
import com.fitmate.myfit.application.port.`in`.certification.command.*
import com.fitmate.myfit.application.port.`in`.certification.response.DeleteFitCertificationResponseDto
import com.fitmate.myfit.application.port.`in`.certification.response.FitCertificationDetailProgressResponseDto
import com.fitmate.myfit.application.port.`in`.certification.response.FitCertificationDetailResponseDto
import com.fitmate.myfit.application.port.`in`.certification.response.RegisterFitCertificationResponseDto
import com.fitmate.myfit.common.exceptions.BadRequestException

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

        fun detailRequestToCommand(fitCertificationId: Long): FitCertificationDetailCommand =
            FitCertificationDetailCommand(fitCertificationId)

        fun dtoToDetailResponse(dto: FitCertificationDetailResponseDto): FitCertificationDetailResponse =
            FitCertificationDetailResponse(
                dto.fitGroupId,
                dto.fitCertificationId,
                dto.certificationStatus,
                dto.state,
                dto.createdAt,
                dto.voteEndDate
            )

        fun detailProgressRequestToCommand(fitCertificationId: Long): FitCertificationDetailProgressCommand =
            FitCertificationDetailProgressCommand(fitCertificationId)

        fun dtoToDetailProgressResponse(dto: FitCertificationDetailProgressResponseDto): FitCertificationDetailProgressResponse =
            FitCertificationDetailProgressResponse(
                dto.fitCertificationId,
                dto.agreeCount,
                dto.disagreeCount,
                dto.maxAgreeCount
            )

        fun fitCertificationResultRequestToCommand(
            fitCertificationId: String,
            eventPublisher: String
        ): UpdateFitCertificationResultCommand {
            val fitCertificationIdLong: Long

            try {
                fitCertificationIdLong = fitCertificationId.toLong()
            } catch (exception: Exception) {
                throw BadRequestException("fit certification id must be long")
            }

            return UpdateFitCertificationResultCommand(fitCertificationIdLong, eventPublisher)
        }
    }
}