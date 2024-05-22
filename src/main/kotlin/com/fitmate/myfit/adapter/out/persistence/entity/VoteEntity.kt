package com.fitmate.myfit.adapter.out.persistence.entity

import com.fitmate.myfit.adapter.out.persistence.converter.BooleanNumberConverter
import com.fitmate.myfit.common.GlobalStatus
import com.fitmate.myfit.domain.Vote
import jakarta.persistence.*
import lombok.EqualsAndHashCode
import java.time.Instant

@Entity
@Table(indexes = [Index(columnList = "userId"), Index(columnList = "targetId")])
@EqualsAndHashCode
class VoteEntity private constructor(
    @Column(nullable = false) val userId: Int,
    @Convert(converter = BooleanNumberConverter::class)
    var agree: Boolean,
    /**
     * target category - 1: fit certification
     */
    @Column(nullable = false) val targetCategory: Int,
    @Column(nullable = false) val targetId: Long
) : BaseEntity(GlobalStatus.PERSISTENCE_NOT_DELETED, createdAt = Instant.now(), createUser = userId.toString()) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun domainToEntity(vote: Vote): VoteEntity {
            val voteEntity = VoteEntity(
                vote.userId,
                vote.agree,
                vote.targetCategory,
                vote.targetId
            )

            voteEntity.id = vote.id
            voteEntity.setMetaDataByDomain(vote)

            return voteEntity
        }
    }
}