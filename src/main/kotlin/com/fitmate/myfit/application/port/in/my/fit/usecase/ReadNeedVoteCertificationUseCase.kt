package com.fitmate.myfit.application.port.`in`.my.fit.usecase

import com.fitmate.myfit.application.port.`in`.my.fit.command.NeedVoteCertificationFilterCommand
import com.fitmate.myfit.application.port.`in`.my.fit.response.NeedVoteCertificationFitGroupResponseDto

interface ReadNeedVoteCertificationUseCase {

    /**
     * Get Filtered need vote certification list use case
     *
     * @param command filter condition with user id
     * @return content list
     */
    fun filterNeedVoteCertification(
        command: NeedVoteCertificationFilterCommand
    ): List<NeedVoteCertificationFitGroupResponseDto>
}