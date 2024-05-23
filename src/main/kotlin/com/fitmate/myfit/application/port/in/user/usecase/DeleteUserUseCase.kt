package com.fitmate.myfit.application.port.`in`.user.usecase

import com.fitmate.myfit.application.port.`in`.user.command.DeleteUserForReadCommand
import com.fitmate.myfit.application.port.`in`.user.response.DeleteUserForReadResponseDto

interface DeleteUserUseCase {

    /**
     * delete user data if  exist.
     *
     * @param deleteUserForReadCommand user Id
     */
    fun deleteUser(deleteUserForReadCommand: DeleteUserForReadCommand): DeleteUserForReadResponseDto
}