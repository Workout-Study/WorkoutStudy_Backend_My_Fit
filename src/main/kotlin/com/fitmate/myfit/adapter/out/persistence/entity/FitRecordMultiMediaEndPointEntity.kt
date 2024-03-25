package com.fitmate.myfit.adapter.out.persistence.entity

import com.fitmate.myfit.common.GlobalStatus
import com.fitmate.myfit.domain.FitRecordMultiMediaEndPoint
import jakarta.persistence.*
import lombok.EqualsAndHashCode
import java.time.Instant

@Entity
@EqualsAndHashCode
class FitRecordMultiMediaEndPointEntity(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fit_record_id", nullable = false)
    val fitRecordEntity: FitRecordEntity,
    val endPoint: String,
    createUser: String
) : BaseEntity(GlobalStatus.PERSISTENCE_NOT_DELETED, Instant.now(), createUser) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun domainToEntity(domain: FitRecordMultiMediaEndPoint): FitRecordMultiMediaEndPointEntity {
            val entity = FitRecordMultiMediaEndPointEntity(
                FitRecordEntity.domainToEntity(domain.fitRecord),
                domain.endPoint,
                domain.createUser
            )

            entity.id = domain.id
            entity.setMetaDataByDomain(domain)

            return entity
        }
    }
}