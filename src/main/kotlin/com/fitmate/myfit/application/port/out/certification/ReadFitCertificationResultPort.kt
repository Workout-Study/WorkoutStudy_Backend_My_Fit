package com.fitmate.myfit.application.port.out.certification

import com.fitmate.myfit.application.service.dto.FitCertificationResultResponseDto

interface ReadFitCertificationResultPort {
    fun findByFitCertificationId(fitCertificationId: Long): FitCertificationResultResponseDto
}