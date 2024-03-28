package com.fitmate.myfit.adapter.out.persistence.entity

import com.fitmate.myfit.common.GlobalStatus
import com.fitmate.myfit.domain.CertificationStatus
import com.fitmate.myfit.domain.FitCertification
import jakarta.persistence.*
import lombok.EqualsAndHashCode
import java.time.Instant

@Entity
@Table(indexes = [Index(columnList = "fitGroupId")])
@EqualsAndHashCode
class FitCertificationEntity private constructor(
    val userId: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fit_record_id", nullable = false)
    val fitRecordEntity: FitRecordEntity,
    val fitGroupId: Long,
    @Enumerated(EnumType.STRING)
    var certificationStatus: CertificationStatus
) : BaseEntity(GlobalStatus.PERSISTENCE_NOT_DELETED, Instant.now(), userId) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun domainToEntity(domain: FitCertification): FitCertificationEntity {
            val fitCertificationEntity = FitCertificationEntity(
                domain.userId,
                FitRecordEntity.domainToEntity(domain.fitRecord),
                domain.fitGroupId,
                domain.certificationStatus
            )

            fitCertificationEntity.id = domain.id
            fitCertificationEntity.setMetaDataByDomain(domain)

            return fitCertificationEntity
        }
    }
}