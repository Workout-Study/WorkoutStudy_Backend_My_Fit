package com.fitmate.myfit.domain

import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordMultiMediaEndPointEntity
import com.fitmate.myfit.common.GlobalStatus
import lombok.EqualsAndHashCode
import java.time.Instant

@EqualsAndHashCode
class FitRecordMultiMediaEndPoint private constructor(
    val id: Long?,
    val fitRecord: FitRecord,
    val endPoint: String,
    createUser: String
) : BaseDomain(GlobalStatus.PERSISTENCE_NOT_DELETED, createdAt = Instant.now(), createUser = createUser) {

    companion object {
        fun createFitRecord(fitRecord: FitRecord, endPoint: String): FitRecordMultiMediaEndPoint {
            return FitRecordMultiMediaEndPoint(
                null,
                fitRecord,
                endPoint,
                fitRecord.userId.toString()
            )
        }

        fun entityToDomain(entity: FitRecordMultiMediaEndPointEntity): FitRecordMultiMediaEndPoint {
            val fitRecordMultiMediaEndPoint = FitRecordMultiMediaEndPoint(
                entity.id,
                FitRecord.entityToDomain(entity.fitRecordEntity),
                entity.endPoint,
                entity.createUser
            )

            fitRecordMultiMediaEndPoint.setMetaDataByEntity(entity)

            return fitRecordMultiMediaEndPoint
        }
    }
}