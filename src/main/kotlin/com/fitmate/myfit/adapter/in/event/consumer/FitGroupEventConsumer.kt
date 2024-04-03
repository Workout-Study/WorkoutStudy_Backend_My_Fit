package com.fitmate.myfit.adapter.`in`.event.consumer

import com.fitmate.myfit.adapter.`in`.event.mapper.FitGroupForReadMapper
import com.fitmate.myfit.application.port.`in`.fit.group.usecase.SaveFitGroupUseCase
import com.fitmate.myfit.common.GlobalStatus
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class FitGroupEventConsumer(private val saveFitGroupUseCase: SaveFitGroupUseCase) {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(FitGroupEventConsumer::class.java)
    }

    /**
     * kafka fit group event listener inbound
     *
     * @param fitGroupId fit group id where an event occurred
     */
    @KafkaListener(topics = [GlobalStatus.KAFKA_TOPIC_FIT_GROUP], groupId = GlobalStatus.KAFKA_GROUP_ID)
    fun fitGroupListener(fitGroupId: String) {
        logger?.info("KafkaListener FitGroupEvent with fitGroupListener start - fit group id = {}", fitGroupId)
        val saveFitGroupForReadCommand = FitGroupForReadMapper.saveFitGroupRequestToCommand(fitGroupId, "kafka")
        saveFitGroupUseCase.saveFitGroupForRead(saveFitGroupForReadCommand)
    }
}