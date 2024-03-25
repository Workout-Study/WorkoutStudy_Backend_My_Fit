package com.fitmate.myfit.adapter.out.persistence.entity

import com.fitmate.myfit.common.GlobalStatus
import com.fitmate.myfit.domain.FitRecord
import jakarta.persistence.*
import lombok.EqualsAndHashCode
import java.time.Instant

@Entity
@EqualsAndHashCode
class FitRecordEntity(
    @Column(nullable = false) var userId: String,
    @Column(nullable = false) var recordStartDate: Instant,
    @Column(nullable = false) var recordEndDate: Instant
) : BaseEntity(GlobalStatus.PERSISTENCE_NOT_DELETED, Instant.now(), userId) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun domainToEntity(fitRecord: FitRecord): FitRecordEntity {
            val fitRecordEntity = FitRecordEntity(
                fitRecord.userId,
                fitRecord.recordStartDate,
                fitRecord.recordEndDate
            )

            fitRecordEntity.id = fitRecord.id
            fitRecordEntity.setMetaDataByDomain(fitRecord)

            return fitRecordEntity
        }
    }
}