package com.fitmate.myfit.application.port.out.certification

import com.fitmate.myfit.domain.CertificationStatus
import com.fitmate.myfit.domain.FitCertification
import com.fitmate.myfit.domain.FitRecord
import java.util.*

interface ReadFitCertificationPort {
    fun findByFitRecordAndFitGroupIdAndCertificationStatusNot(
        fitRecord: FitRecord,
        fitGroupId: Long,
        certificationStatus: CertificationStatus
    ): List<FitCertification>

    fun findByUserIdAndFitGroupIdAndCertificationStatus(
        userId: String,
        fitGroupId: Long,
        certificationStatus: CertificationStatus
    ): Optional<FitCertification>

    fun findById(fitCertificationId: Long): Optional<FitCertification>

    fun findByFitRecordAndCertificationStatusNot(
        fitRecord: FitRecord,
        certificationStatus: CertificationStatus
    ): List<FitCertification>
}