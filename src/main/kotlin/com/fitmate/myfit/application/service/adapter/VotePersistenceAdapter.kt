package com.fitmate.myfit.application.service.adapter

import com.fitmate.myfit.adapter.out.persistence.entity.VoteEntity
import com.fitmate.myfit.adapter.out.persistence.repository.VoteRepository
import com.fitmate.myfit.application.port.out.vote.ReadVotePort
import com.fitmate.myfit.application.port.out.vote.RegisterVotePort
import com.fitmate.myfit.application.port.out.vote.UpdateVotePort
import com.fitmate.myfit.common.GlobalStatus
import com.fitmate.myfit.domain.Vote
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class VotePersistenceAdapter(private val voteRepository: VoteRepository) : RegisterVotePort, ReadVotePort,
    UpdateVotePort {
    @Transactional(readOnly = true)
    override fun findByUserIdAndTargetCategoryAndTargetId(
        userId: Int,
        targetCategory: Int,
        targetId: Long
    ): Optional<Vote> {
        val voteEntityOpt = voteRepository.findByUserIdAndTargetCategoryAndTargetIdAndState(
            userId,
            targetCategory,
            targetId,
            GlobalStatus.PERSISTENCE_NOT_DELETED
        )

        return if (voteEntityOpt.isPresent) {
            Optional.of(
                Vote.entityToDomain(voteEntityOpt.get())
            )
        } else Optional.empty()
    }

    @Transactional(readOnly = true)
    override fun countByAgreeAndTargetCategoryAndTargetId(agree: Boolean, targetCategory: Int, targetId: Long): Int =
        voteRepository.countByAgreeAndTargetCategoryAndTargetIdAndState(
            agree,
            targetCategory,
            targetId,
            GlobalStatus.PERSISTENCE_NOT_DELETED
        )

    @Transactional
    override fun registerVote(vote: Vote): Vote {
        val voteEntity = VoteEntity.domainToEntity(vote)
        val savedVoteEntity = voteRepository.save(voteEntity)
        return Vote.entityToDomain(savedVoteEntity)
    }

    @Transactional
    override fun updateVote(vote: Vote) {
        val voteEntity = VoteEntity.domainToEntity(vote)
        voteRepository.save(voteEntity)
    }
}