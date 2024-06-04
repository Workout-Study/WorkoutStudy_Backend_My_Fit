package com.fitmate.myfit.domain

import com.fitmate.myfit.adapter.out.persistence.entity.UserForReadEntity
import com.fitmate.myfit.application.port.`in`.user.command.CreateUserForReadCommand
import com.fitmate.myfit.application.port.`in`.user.command.SaveUserForReadCommand
import com.fitmate.myfit.application.service.dto.UserInfoResponseDto
import com.fitmate.myfit.common.GlobalStatus
import java.time.Instant

class UserForRead(
    val id: Long?,
    val userId: Int,
    var nickname: String,
    createUser: String
) : BaseDomain(GlobalStatus.PERSISTENCE_NOT_DELETED, createdAt = Instant.now(), createUser = userId.toString()) {
    fun updateByUserInfo(userInfo: UserInfoResponseDto, saveUserForReadCommand: SaveUserForReadCommand) {
        this.nickname = userInfo.nickname
        this.state = userInfo.state
        this.updatedAt = Instant.now()
        this.updateUser = saveUserForReadCommand.eventPublisher
    }

    fun updateByUserCommand(createUserForReadCommand: CreateUserForReadCommand) {
        this.nickname = createUserForReadCommand.nickname
        this.state = createUserForReadCommand.state
        this.updatedAt = Instant.now()
        this.updateUser = createUserForReadCommand.eventPublisher
    }

    companion object {

        fun entityToDomain(entity: UserForReadEntity): UserForRead {
            val userForRead = UserForRead(
                entity.id,
                entity.userId,
                entity.nickname,
                entity.createUser
            )

            userForRead.setMetaDataByEntity(entity)

            return userForRead
        }

        fun createByUserInfo(
            userInfo: UserInfoResponseDto,
            saveUserForReadCommand: SaveUserForReadCommand
        ): UserForRead {
            val userForRead = UserForRead(
                null,
                userInfo.userId,
                userInfo.nickname,
                saveUserForReadCommand.eventPublisher
            )

            userForRead.state = userInfo.state

            return userForRead
        }

        fun createByUserCommand(command: CreateUserForReadCommand): UserForRead {
            val userForRead = UserForRead(
                null,
                command.userId,
                command.nickname,
                command.eventPublisher
            )

            userForRead.state = command.state

            return userForRead
        }
    }
}