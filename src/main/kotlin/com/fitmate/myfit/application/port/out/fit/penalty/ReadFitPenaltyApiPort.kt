package com.fitmate.myfit.application.port.out.fit.penalty

import com.fitmate.myfit.application.service.dto.FitPenaltyDetailResponseDto

interface ReadFitPenaltyApiPort {
    fun findByFitPenaltyId(fitPenaltyId: Long): FitPenaltyDetailResponseDto
}