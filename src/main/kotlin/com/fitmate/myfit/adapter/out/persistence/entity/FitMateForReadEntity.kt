package com.fitmate.myfit.adapter.out.persistence.entity

import com.fitmate.myfit.domain.FitMateForRead
import jakarta.persistence.*
import java.time.Instant

@Entity
class FitMateForReadEntity private constructor(
    val fitGroupId: Long,
    @Column(unique = true) val fitMateId: Long,
    val fitMateUserId: Int,
    state: Boolean,
    createUser: String
) : BaseEntity(state, createdAt = Instant.now(), createUser) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {

        fun domainToEntity(domain: FitMateForRead): FitMateForReadEntity {
            val fitMateForReadEntity = FitMateForReadEntity(
                domain.fitGroupId,
                domain.fitMateId,
                domain.fitMateUserId,
                domain.state,
                domain.createUser
            )

            fitMateForReadEntity.id = domain.id
            fitMateForReadEntity.setMetaDataByDomain(domain)

            return fitMateForReadEntity
        }
    }
}
