package com.fitmate.myfit.application.port.`in`.certification.usecase

import com.fitmate.myfit.application.port.`in`.certification.command.RegisterFitCertificationCommand
import com.fitmate.myfit.application.port.`in`.certification.response.RegisterFitCertificationResponseDto

interface RegisterFitCertificationUseCase {

    /**
     * Register fit certification use case,
     * register fit certification data to persistence
     *
     * @param registerFitCertificationCommand data about register fit certification with user id
     * @return Boolean about register success
     */
    fun registerFitCertification(registerFitCertificationCommand: RegisterFitCertificationCommand): RegisterFitCertificationResponseDto
}