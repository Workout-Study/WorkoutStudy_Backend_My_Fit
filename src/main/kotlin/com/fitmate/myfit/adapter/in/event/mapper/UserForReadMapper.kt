package com.fitmate.myfit.adapter.`in`.event.mapper

import com.fitmate.myfit.application.port.`in`.user.command.DeleteUserForReadCommand
import com.fitmate.myfit.application.port.`in`.user.command.SaveUserForReadCommand
import com.fitmate.myfit.application.port.`in`.user.response.DeleteUserForReadResponseDto
import com.fitmate.myfit.application.port.`in`.user.response.SaveUserForReadResponseDto
import com.fitmate.myfit.common.exceptions.BadRequestException

class UserForReadMapper {

    companion object {
        fun saveUserRequestToCommand(userId: String, eventPublisher: String): SaveUserForReadCommand {
            val userIdInt: Int = userIdToInt(userId)

            return SaveUserForReadCommand(
                userIdInt,
                eventPublisher
            )
        }

        private fun userIdToInt(userId: String): Int {
            val userIdInt: Int

            try {
                userIdInt = userId.toInt()
            } catch (exception: Exception) {
                throw BadRequestException("user id must be int")
            }
            return userIdInt
        }

        fun deleteUserRequestToCommand(userId: String, eventPublisher: String): DeleteUserForReadCommand {
            val userIdInt: Int = userIdToInt(userId)

            return DeleteUserForReadCommand(
                userIdInt,
                eventPublisher
            )
        }

        fun resultToSaveResponseDto(result: Boolean): SaveUserForReadResponseDto =
            SaveUserForReadResponseDto(result)

        fun resultToDeleteResponseDto(result: Boolean): DeleteUserForReadResponseDto =
            DeleteUserForReadResponseDto(result)
    }
}