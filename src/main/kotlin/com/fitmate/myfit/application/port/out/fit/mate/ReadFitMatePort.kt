package com.fitmate.myfit.application.port.out.fit.mate

import com.fitmate.myfit.application.service.dto.FitMateDetailsResponseDto

interface ReadFitMatePort {
    fun findByFitGroupId(fitGroupId: Long): FitMateDetailsResponseDto
}