package com.fitmate.myfit.application.service.adapter

import com.fitmate.myfit.adapter.out.persistence.entity.UserForReadEntity
import com.fitmate.myfit.adapter.out.persistence.repository.UserForReadRepository
import com.fitmate.myfit.application.port.out.user.ReadUserForReadPort
import com.fitmate.myfit.application.port.out.user.SaveUserForReadPort
import com.fitmate.myfit.common.GlobalStatus
import com.fitmate.myfit.domain.UserForRead
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class UserPForReadPersistenceAdapter(
    private val userForReadRepository: UserForReadRepository
) : ReadUserForReadPort, SaveUserForReadPort {

    @Transactional(readOnly = true)
    override fun findByUserId(userId: Int): Optional<UserForRead> {
        val userForReadEntityOpt =
            userForReadRepository.findByUserIdAndState(userId, GlobalStatus.PERSISTENCE_NOT_DELETED)

        return if (userForReadEntityOpt.isPresent) {
            Optional.of(
                UserForRead.entityToDomain(userForReadEntityOpt.get())
            )
        } else Optional.empty()
    }

    @Transactional
    override fun saveUserForRead(userForRead: UserForRead): UserForRead {
        val userForReadEntity = UserForReadEntity.domainToEntity(userForRead)
        val savedUserForReadEntity: UserForReadEntity = userForReadRepository.save(userForReadEntity)
        return UserForRead.entityToDomain(savedUserForReadEntity)
    }
}