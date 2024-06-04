package com.fitmate.myfit.adapter.`in`.event.consumer

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.myfit.adapter.`in`.event.dto.UserCreateMessageDto
import com.fitmate.myfit.adapter.`in`.event.mapper.UserForReadMapper
import com.fitmate.myfit.application.port.`in`.user.usecase.SaveUserUseCase
import com.fitmate.myfit.common.GlobalStatus
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UserEventConsumer(
    private val saveUserUseCase: SaveUserUseCase,
    private val objectMapper: ObjectMapper
) {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(UserEventConsumer::class.java)
    }

    /**
     * kafka user update, delete event listener inbound
     *
     * @param userId user id where an event occurred
     */
    @KafkaListener(
        topics = [GlobalStatus.KAFKA_TOPIC_USER_INFO_EVENT],
        groupId = GlobalStatus.KAFKA_GROUP_ID
    )
    fun userEvent(userId: String) {
        logger?.info("KafkaListener userEvent with userEvent start - user id = {}", userId)
        val saveUserForReadCommand = UserForReadMapper.saveUserRequestToCommand(userId, "kafka")
        saveUserUseCase.saveUser(saveUserForReadCommand)
    }

    /**
     * kafka user create event listener inbound
     *
     * @param userId user id where an event occurred
     */
    @KafkaListener(
        topics = [GlobalStatus.KAFKA_TOPIC_USER_CREATE_EVENT],
        groupId = GlobalStatus.KAFKA_GROUP_ID
    )
    fun userCreateEvent(message: String) {
        logger?.info("KafkaListener userCreateEvent with userCreateEvent start - message = {}", message)

        val userCreateMessageDto: UserCreateMessageDto

        try {
            userCreateMessageDto = objectMapper.readValue(message, UserCreateMessageDto::class.java)
        } catch (e: JsonProcessingException) {
            logger?.error("JsonProcessingException on userCreateEvent ", e)

            throw e
        } catch (e: JsonMappingException) {
            logger?.error("JsonMappingException on userCreateEvent", e)

            throw e
        }

        val createUserForReadCommand = UserForReadMapper.createUserRequestToCommand(userCreateMessageDto, "kafka")
        saveUserUseCase.createUser(createUserForReadCommand)
    }
}