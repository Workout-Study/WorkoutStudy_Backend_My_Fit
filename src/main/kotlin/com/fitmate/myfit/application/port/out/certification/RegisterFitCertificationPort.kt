package com.fitmate.myfit.application.port.out.certification

import com.fitmate.myfit.domain.FitCertification

interface RegisterFitCertificationPort {
    fun registerFitCertification(fitCertification: FitCertification): FitCertification
}