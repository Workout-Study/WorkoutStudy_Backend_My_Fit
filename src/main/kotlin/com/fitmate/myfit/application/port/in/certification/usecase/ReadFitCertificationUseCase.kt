package com.fitmate.myfit.application.port.`in`.certification.usecase

import com.fitmate.myfit.application.port.`in`.my.fit.command.FitCertificationFilterByGroupCommand
import com.fitmate.myfit.application.port.`in`.my.fit.response.FitCertificationDetailResponseDto

interface ReadFitCertificationUseCase {
    fun getFitCertificationByGroupId(command: FitCertificationFilterByGroupCommand): List<FitCertificationDetailResponseDto>
}