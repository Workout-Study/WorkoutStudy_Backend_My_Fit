package com.fitmate.myfit.application.service.service

import com.fitmate.myfit.adapter.`in`.event.mapper.UserForReadMapper
import com.fitmate.myfit.application.port.`in`.user.command.SaveUserForReadCommand
import com.fitmate.myfit.application.port.`in`.user.response.SaveUserForReadResponseDto
import com.fitmate.myfit.application.port.`in`.user.usecase.SaveUserUseCase
import com.fitmate.myfit.application.port.out.user.ReadUserForReadPort
import com.fitmate.myfit.application.port.out.user.ReadUserPort
import com.fitmate.myfit.application.port.out.user.SaveUserForReadPort
import com.fitmate.myfit.domain.UserForRead
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserForReadService(
    private val readUserForReadPort: ReadUserForReadPort,
    private val saveUserForReadPort: SaveUserForReadPort,
    private val readUserPort: ReadUserPort
) : SaveUserUseCase {

    /**
     * register user data if not exist.
     * if exist update user data
     *
     * @param saveUserForReadCommand data about user
     */
    @Transactional
    override fun saveUser(saveUserForReadCommand: SaveUserForReadCommand): SaveUserForReadResponseDto {
        val userInfo = readUserPort.findByUserId(saveUserForReadCommand.userId)

        val userForRead =
            readUserForReadPort.findByUserId(userInfo.userId)
                .orElse(UserForRead.createByUserInfo(userInfo, saveUserForReadCommand))

        userForRead.updateByUserInfo(userInfo, saveUserForReadCommand)
        val savedUserForRead = saveUserForReadPort.saveUserForRead(userForRead)

        return UserForReadMapper.resultToSaveResponseDto(savedUserForRead.id != null)
    }
}