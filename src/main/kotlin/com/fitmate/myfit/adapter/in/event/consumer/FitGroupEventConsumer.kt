package com.fitmate.myfit.adapter.`in`.event.consumer

import com.fitmate.myfit.adapter.`in`.event.dto.FitGroupDto
import com.fitmate.myfit.adapter.`in`.event.mapper.FitGroupForReadMapper
import com.fitmate.myfit.application.port.`in`.fit.group.usecase.RegisterFitGroupUseCase
import com.fitmate.myfit.common.GlobalStatus
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class FitGroupEventConsumer(private val registerFitGroupUseCase: RegisterFitGroupUseCase) {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(FitGroupEventConsumer::class.java)
    }

    /**
     * kafka event listener inbound
     *
     * @param fitGroupDto data about fit group
     */
    @KafkaListener(topics = [GlobalStatus.KAFKA_TOPIC_FIT_GROUP], groupId = GlobalStatus.KAFKA_GROUP_ID)
    fun fitGroupListener(fitGroupDto: FitGroupDto) {
        logger?.info(
            "KafkaListener FitGroupEvent with fitGroupListener start - fit group id = {}, data = {}",
            fitGroupDto.fitGroupId,
            fitGroupDto
        )
        val saveFitGroupForReadCommand = FitGroupForReadMapper.saveDtoToCommand(fitGroupDto, "kafka")
        registerFitGroupUseCase.saveFitGroupForRead(saveFitGroupForReadCommand)
    }
}