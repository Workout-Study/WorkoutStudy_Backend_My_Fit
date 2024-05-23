package com.fitmate.myfit.adapter.out.persistence.repository

import com.fitmate.myfit.adapter.out.persistence.entity.UserForReadEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserForReadRepository : JpaRepository<UserForReadEntity, Int> {
    fun findByUserIdAndState(userIdInt: Int, state: Boolean): Optional<UserForReadEntity>
}