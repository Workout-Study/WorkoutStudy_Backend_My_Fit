package com.fitmate.myfit.adapter.out.persistence.entity

import com.fitmate.myfit.common.GlobalStatus
import com.fitmate.myfit.domain.UserForRead
import jakarta.persistence.*
import java.time.Instant

@Entity
class UserForReadEntity(
    @Column(nullable = false) val userId: Int,
    @Column(nullable = false) var nickname: String,
    createUser: String
) : BaseEntity(GlobalStatus.PERSISTENCE_NOT_DELETED, Instant.now(), createUser) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {

        fun domainToEntity(domain: UserForRead): UserForReadEntity {
            val userForReadEntity = UserForReadEntity(
                domain.userId,
                domain.nickname,
                domain.createUser
            )

            userForReadEntity.id = domain.id
            userForReadEntity.setMetaDataByDomain(domain)

            return userForReadEntity
        }
    }
}