package com.fitmate.myfit.adapter.out.persistence.repository

import com.fitmate.myfit.adapter.out.persistence.entity.VoteEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface VoteRepository : JpaRepository<VoteEntity, Long> {
    fun findByUserIdAndTargetCategoryAndTargetIdAndState(
        userId: String,
        targetCategory: Int,
        targetId: Long,
        state: Boolean
    ): Optional<VoteEntity>
}