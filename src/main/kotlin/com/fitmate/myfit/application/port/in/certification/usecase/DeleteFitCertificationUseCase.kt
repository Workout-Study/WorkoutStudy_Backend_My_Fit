package com.fitmate.myfit.application.port.`in`.certification.usecase

import com.fitmate.myfit.application.port.`in`.certification.command.DeleteFitCertificationCommand
import com.fitmate.myfit.application.port.`in`.certification.response.DeleteFitCertificationResponseDto

interface DeleteFitCertificationUseCase {

    /**
     * Delete fit certification use case,
     * delete fit certification data to persistence
     *
     * @param deleteFitCertificationCommand data about delete fit certification id with user id
     * @return Boolean about delete success
     */
    fun deleteFitCertification(deleteFitCertificationCommand: DeleteFitCertificationCommand): DeleteFitCertificationResponseDto
}