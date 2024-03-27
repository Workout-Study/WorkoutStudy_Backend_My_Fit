package com.fitmate.myfit.application.port.out.fit.record

import com.fitmate.myfit.domain.CertificationStatus
import com.fitmate.myfit.domain.FitCertification
import com.fitmate.myfit.domain.FitRecord
import java.util.*

interface ReadFitCertificationPort {
    fun findByFitRecordAndFitGroupId(fitRecord: FitRecord, fitGroupId: Long): Optional<FitCertification>

    fun findByUserIdAndFitGroupIdAndCertificationStatus(
        userId: String,
        fitGroupId: Long,
        certificationStatus: CertificationStatus
    ): Optional<FitCertification>
}