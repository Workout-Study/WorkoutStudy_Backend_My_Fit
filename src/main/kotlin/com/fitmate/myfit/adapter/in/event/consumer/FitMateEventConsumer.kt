package com.fitmate.myfit.adapter.`in`.event.consumer

import com.fitmate.myfit.adapter.`in`.event.mapper.FitMateForReadMapper
import com.fitmate.myfit.application.port.`in`.fit.mate.usecase.SaveFitMateUseCase
import com.fitmate.myfit.common.GlobalStatus
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class FitMateEventConsumer(private val saveFitMateUseCase: SaveFitMateUseCase) {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(FitMateEventConsumer::class.java)
    }

    /**
     * kafka fit mate event listener inbound
     *
     * @param fitGroupId fit group id where an event occurred for a fit mate
     */
    @KafkaListener(topics = [GlobalStatus.KAFKA_TOPIC_FIT_MATE], groupId = GlobalStatus.KAFKA_GROUP_ID)
    fun fitMateListener(fitGroupId: String) {
        logger?.info("KafkaListener FitMateEvent with fitMateListener start - fit group id = {}", fitGroupId)
        val saveFitMateForReadCommand = FitMateForReadMapper.saveFitMateRequestToCommand(fitGroupId, "kafka")
        saveFitMateUseCase.saveFitMateForRead(saveFitMateForReadCommand)
    }
}