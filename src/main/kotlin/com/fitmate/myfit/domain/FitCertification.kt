package com.fitmate.myfit.domain

import com.fitmate.myfit.adapter.out.persistence.entity.FitCertificationEntity
import com.fitmate.myfit.application.port.`in`.certification.command.RegisterFitCertificationCommand
import com.fitmate.myfit.common.GlobalStatus
import java.time.Instant

class FitCertification private constructor(
    val id: Long?,
    val userId: String,
    val fitRecord: FitRecord,
    val fitGroupId: Long,
    var certificationStatus: CertificationStatus = CertificationStatus.REQUESTED
) : BaseDomain(GlobalStatus.PERSISTENCE_NOT_DELETED, createdAt = Instant.now(), createUser = userId) {

    companion object {
        fun createFitCertificationsByCommand(
            fitRecord: FitRecord,
            command: RegisterFitCertificationCommand
        ): List<FitCertification> =
            command.fitGroupIds.map {
                FitCertification(
                    null,
                    command.requestUserId,
                    fitRecord,
                    it
                )
            }


        fun entityToDomain(entity: FitCertificationEntity): FitCertification {
            val fitCertification = FitCertification(
                entity.id,
                entity.userId,
                FitRecord.entityToDomain(entity.fitRecordEntity),
                entity.fitGroupId,
                entity.certificationStatus
            )

            fitCertification.setMetaDataByEntity(entity)

            return fitCertification
        }
    }
}