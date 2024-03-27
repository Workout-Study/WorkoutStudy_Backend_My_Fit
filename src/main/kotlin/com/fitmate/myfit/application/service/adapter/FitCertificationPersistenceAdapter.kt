package com.fitmate.myfit.application.service.adapter

import com.fitmate.myfit.adapter.out.persistence.entity.FitCertificationEntity
import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.adapter.out.persistence.repository.FitCertificationRepository
import com.fitmate.myfit.application.port.out.fit.record.ReadFitCertificationPort
import com.fitmate.myfit.application.port.out.fit.record.RegisterFitCertificationPort
import com.fitmate.myfit.common.GlobalStatus
import com.fitmate.myfit.domain.CertificationStatus
import com.fitmate.myfit.domain.FitCertification
import com.fitmate.myfit.domain.FitRecord
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class FitCertificationPersistenceAdapter(
    private val fitCertificationRepository: FitCertificationRepository
) : ReadFitCertificationPort, RegisterFitCertificationPort {

    @Transactional
    override fun registerFitCertification(fitCertification: FitCertification): FitCertification {
        val fitCertificationEntity = FitCertificationEntity.domainToEntity(fitCertification)
        val savedFitCertificationEntity = fitCertificationRepository.save(fitCertificationEntity)
        return FitCertification.entityToDomain(savedFitCertificationEntity)
    }

    @Transactional(readOnly = true)
    override fun findByFitRecordAndFitGroupId(fitRecord: FitRecord, fitGroupId: Long): Optional<FitCertification> {
        val fitRecordEntity = FitRecordEntity.domainToEntity(fitRecord)

        val fitCertificationEntityOpt = fitCertificationRepository.findByFitRecordEntityAndFitGroupIdAndState(
            fitRecordEntity,
            fitGroupId,
            GlobalStatus.PERSISTENCE_NOT_DELETED
        )

        return if (fitCertificationEntityOpt.isPresent) {
            Optional.of(
                FitCertification.entityToDomain(fitCertificationEntityOpt.get())
            )
        } else Optional.empty()
    }

    @Transactional(readOnly = true)
    override fun findByUserIdAndFitGroupIdAndCertificationStatus(
        userId: String,
        fitGroupId: Long,
        certificationStatus: CertificationStatus
    ): Optional<FitCertification> {
        val fitCertificationEntityOpt =
            fitCertificationRepository.findByUserIdAndFitGroupIdAndCertificationStatusAndState(
                userId,
                fitGroupId,
                certificationStatus,
                GlobalStatus.PERSISTENCE_NOT_DELETED
            )

        return if (fitCertificationEntityOpt.isPresent) {
            Optional.of(
                FitCertification.entityToDomain(fitCertificationEntityOpt.get())
            )
        } else Optional.empty()
    }
}