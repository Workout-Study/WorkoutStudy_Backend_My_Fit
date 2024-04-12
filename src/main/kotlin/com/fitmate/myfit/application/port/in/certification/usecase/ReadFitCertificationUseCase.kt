package com.fitmate.myfit.application.port.`in`.certification.usecase

import com.fitmate.myfit.adapter.`in`.web.certification.response.FitCertificationProgressesResponse
import com.fitmate.myfit.application.port.`in`.certification.command.FitCertificationProgressByGroupIdCommand
import com.fitmate.myfit.application.port.`in`.certification.response.FitCertificationDetailResponseDto
import com.fitmate.myfit.application.port.`in`.my.fit.command.FitCertificationFilterByGroupCommand

interface ReadFitCertificationUseCase {
    fun getFitCertificationByGroupId(command: FitCertificationFilterByGroupCommand): List<FitCertificationDetailResponseDto>

    fun getFitCertificationProgressByGroupId(command: FitCertificationProgressByGroupIdCommand): FitCertificationProgressesResponse
}