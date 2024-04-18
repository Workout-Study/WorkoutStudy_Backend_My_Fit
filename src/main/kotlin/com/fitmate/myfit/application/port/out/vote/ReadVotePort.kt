package com.fitmate.myfit.application.port.out.vote

import com.fitmate.myfit.domain.Vote
import java.util.*

interface ReadVotePort {
    fun findByUserIdAndTargetCategoryAndTargetId(userId: String, targetCategory: Int, targetId: Long): Optional<Vote>

    fun countByAgreeAndTargetCategoryAndTargetId(agree: Boolean, targetCategory: Int, targetId: Long): Int
}