package com.fitmate.myfit.domain

import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.application.port.`in`.command.RegisterFitRecordCommand
import com.fitmate.myfit.common.GlobalStatus
import lombok.EqualsAndHashCode
import java.time.Instant

@EqualsAndHashCode
class FitRecord private constructor(
    val id: Long?,
    val userId: String,
    val recordStartDate: Instant,
    val recordEndDate: Instant
) : BaseDomain(GlobalStatus.PERSISTENCE_NOT_DELETED, createdAt = Instant.now(), createUser = userId) {

    companion object {
        fun createByCommand(registerFitRecordCommand: RegisterFitRecordCommand): FitRecord {
            return FitRecord(
                null,
                registerFitRecordCommand.requestUserId,
                registerFitRecordCommand.recordStartDate,
                registerFitRecordCommand.recordEndDate
            )
        }

        fun entityToDomain(fitRecordEntity: FitRecordEntity): FitRecord {
            val fitRecord = FitRecord(
                fitRecordEntity.id,
                fitRecordEntity.userId,
                fitRecordEntity.recordStartDate,
                fitRecordEntity.recordEndDate
            )

            fitRecord.setMetaDataByEntity(fitRecordEntity)

            return fitRecord
        }
    }
}