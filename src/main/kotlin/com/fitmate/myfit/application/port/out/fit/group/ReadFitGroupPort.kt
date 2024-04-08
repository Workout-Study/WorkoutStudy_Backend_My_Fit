package com.fitmate.myfit.application.port.out.fit.group

import com.fitmate.myfit.application.service.dto.FitGroupResponseDto

interface ReadFitGroupPort {
    fun findByFitGroupId(fitGroupId: Long): FitGroupResponseDto
}