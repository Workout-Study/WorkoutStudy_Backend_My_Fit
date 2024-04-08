package com.fitmate.myfit.application.port.out.certification

import com.fitmate.myfit.domain.FitCertification

interface UpdateFitCertificationPort {
    fun updateFitCertification(fitCertification: FitCertification)
}