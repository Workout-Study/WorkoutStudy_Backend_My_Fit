package com.fitmate.myfit.domain

import com.fitmate.myfit.adapter.out.persistence.entity.VoteEntity
import com.fitmate.myfit.application.port.`in`.vote.command.RegisterVoteCommand
import com.fitmate.myfit.application.port.`in`.vote.command.UpdateVoteCommand
import com.fitmate.myfit.common.GlobalStatus
import java.time.Instant

class Vote private constructor(
    val id: Long?,
    val userId: String,
    var agree: Boolean,
    val targetCategory: Int, // target category - 1: fit certification
    val targetId: Long,
) : BaseDomain(GlobalStatus.PERSISTENCE_NOT_DELETED, createdAt = Instant.now(), createUser = userId) {

    fun updateAgree(updateVoteCommand: UpdateVoteCommand) {
        this.agree = updateVoteCommand.agree
        this.updatedAt = Instant.now()
        this.updateUser = updateVoteCommand.requestUserId
    }

    companion object {

        fun createByCommand(command: RegisterVoteCommand): Vote {
            return Vote(
                null,
                command.requestUserId,
                command.agree,
                command.targetCategory,
                command.targetId
            )
        }

        fun entityToDomain(voteEntity: VoteEntity): Vote {
            val vote = Vote(
                voteEntity.id,
                voteEntity.userId,
                voteEntity.agree,
                voteEntity.targetCategory,
                voteEntity.targetId
            )

            vote.setMetaDataByEntity(voteEntity)

            return vote
        }
    }
}