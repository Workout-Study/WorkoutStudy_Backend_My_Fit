package com.fitmate.myfit.adapter.out.persistence.entity

import com.fitmate.myfit.domain.FitGroupForRead
import jakarta.persistence.*
import lombok.EqualsAndHashCode
import java.time.Instant

@Entity
@EqualsAndHashCode
class FitGroupForReadEntity private constructor(
    @Column(unique = true) val fitGroupId: Long,
    val fitGroupName: String,
    val cycle: Int,
    val frequency: Int,
    state: Boolean,
    createUser: String
) : BaseEntity(state, createdAt = Instant.now(), createUser) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {

        fun domainToEntity(domain: FitGroupForRead): FitGroupForReadEntity {
            val fitGroupForReadEntity = FitGroupForReadEntity(
                domain.fitGroupId,
                domain.fitGroupName,
                domain.cycle,
                domain.frequency,
                domain.state,
                domain.createUser
            )

            fitGroupForReadEntity.id = domain.id
            fitGroupForReadEntity.setMetaDataByDomain(domain)

            return fitGroupForReadEntity
        }
    }
}