package com.fitmate.myfit.adapter.out.persistence.entity

import com.fitmate.myfit.common.GlobalStatus
import com.fitmate.myfit.domain.FitOff
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(indexes = [Index(columnList = "userId")])
class FitOffEntity(
    @Column(nullable = false) var userId: Int,
    @Column(nullable = false) var fitOffStartDate: Instant,
    @Column(nullable = false) var fitOffEndDate: Instant,
    @Column var fitOffReason: String
) : BaseEntity(GlobalStatus.PERSISTENCE_NOT_DELETED, Instant.now(), userId.toString()) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun domainToEntity(domain: FitOff): FitOffEntity {
            val fitOffEntity = FitOffEntity(
                domain.userId,
                domain.fitOffStartDate,
                domain.fitOffEndDate,
                domain.fitOffReason
            )

            fitOffEntity.id = domain.id
            fitOffEntity.setMetaDataByDomain(domain)

            return fitOffEntity
        }
    }
}