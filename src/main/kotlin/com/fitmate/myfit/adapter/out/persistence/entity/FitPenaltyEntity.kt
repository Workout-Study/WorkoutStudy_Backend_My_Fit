package com.fitmate.myfit.adapter.out.persistence.entity

import com.fitmate.myfit.adapter.out.persistence.converter.BooleanNumberConverter
import com.fitmate.myfit.domain.FitPenalty
import jakarta.persistence.*
import lombok.EqualsAndHashCode
import java.time.Instant

@Entity
@Table(indexes = [Index(columnList = "fitGroupId"), Index(columnList = "userId")])
@EqualsAndHashCode
class FitPenaltyEntity(
    @Column(unique = true) val fitPenaltyId: Long,
    val fitGroupId: Long,
    val userId: String,
    val amount: Int,
    @Convert(converter = BooleanNumberConverter::class) var paid: Boolean,
    @Convert(converter = BooleanNumberConverter::class) var penaltyDone: Boolean,
    state: Boolean,
    createUser: String
) : BaseEntity(state, Instant.now(), createUser) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun domainToEntity(domain: FitPenalty): FitPenaltyEntity {
            val fitPenaltyEntity = FitPenaltyEntity(
                domain.fitPenaltyId,
                domain.fitGroupId,
                domain.userId,
                domain.amount,
                domain.paid,
                domain.penaltyDone,
                domain.state,
                domain.createUser,
            )

            fitPenaltyEntity.id = domain.id
            fitPenaltyEntity.setMetaDataByDomain(domain)

            return fitPenaltyEntity
        }
    }
}