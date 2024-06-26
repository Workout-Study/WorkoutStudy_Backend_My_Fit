package com.fitmate.myfit.application.port.`in`.user.usecase

import com.fitmate.myfit.application.port.`in`.user.command.CreateUserForReadCommand
import com.fitmate.myfit.application.port.`in`.user.command.SaveUserForReadCommand
import com.fitmate.myfit.application.port.`in`.user.response.SaveUserForReadResponseDto

interface SaveUserUseCase {

    /**
     * register user data if not exist.
     * if exist update user data
     *
     * @param saveUserForReadCommand data about user
     */
    fun saveUser(saveUserForReadCommand: SaveUserForReadCommand): SaveUserForReadResponseDto

    /**
     * register user data.
     *
     * @param createUserForReadCommand data about user
     */
    fun createUser(createUserForReadCommand: CreateUserForReadCommand): SaveUserForReadResponseDto
}