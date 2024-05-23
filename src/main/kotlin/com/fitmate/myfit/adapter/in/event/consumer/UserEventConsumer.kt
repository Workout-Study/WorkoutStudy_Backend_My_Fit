package com.fitmate.myfit.adapter.`in`.event.consumer

import com.fitmate.myfit.adapter.`in`.event.mapper.UserForReadMapper
import com.fitmate.myfit.application.port.`in`.user.usecase.DeleteUserUseCase
import com.fitmate.myfit.application.port.`in`.user.usecase.SaveUserUseCase
import com.fitmate.myfit.common.GlobalStatus
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UserEventConsumer(
    private val saveUserUseCase: SaveUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(UserEventConsumer::class.java)
    }

    /**
     * kafka user create, update event listener inbound
     *
     * @param userId user id where an event occurred
     */
    @KafkaListener(
        topics = [GlobalStatus.KAFKA_TOPIC_USER_CREATE_EVENT, GlobalStatus.KAFKA_TOPIC_USER_UPDATE_EVENT],
        groupId = GlobalStatus.KAFKA_GROUP_ID
    )
    fun userEvent(userId: String) {
        logger?.info("KafkaListener userEvent with userEvent start - user id = {}", userId)
        val saveUserForReadCommand = UserForReadMapper.saveUserRequestToCommand(userId, "kafka")
        saveUserUseCase.saveUser(saveUserForReadCommand)
    }

    /**
     * kafka user delete event listener inbound
     *
     * @param userId user id where an event occurred
     */
    @KafkaListener(
        topics = [GlobalStatus.KAFKA_TOPIC_USER_DELETE_EVENT],
        groupId = GlobalStatus.KAFKA_GROUP_ID
    )
    fun userDeleteEvent(userId: String) {
        logger?.info("KafkaListener userDeleteEvent with userDeleteEvent start - user id = {}", userId)
        val deleteUserForReadCommand = UserForReadMapper.deleteUserRequestToCommand(userId, "kafka")
        deleteUserUseCase.deleteUser(deleteUserForReadCommand)
    }
}