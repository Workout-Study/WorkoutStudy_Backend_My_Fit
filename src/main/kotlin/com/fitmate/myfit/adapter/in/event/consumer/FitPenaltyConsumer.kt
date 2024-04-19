package com.fitmate.myfit.adapter.`in`.event.consumer

import com.fitmate.myfit.adapter.`in`.event.mapper.FitPenaltyDtoMapper
import com.fitmate.myfit.application.port.`in`.fit.penalty.usecase.SaveFitPenaltyUseCase
import com.fitmate.myfit.common.GlobalStatus
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class FitPenaltyConsumer(
    private val saveFitPenaltyUseCase: SaveFitPenaltyUseCase
) {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(FitPenaltyConsumer::class.java)
    }

    /**
     * kafka fit penalty event listener inbound
     *
     * @param fitPenaltyId fit penalty id where an event occurred
     */
    @KafkaListener(topics = [GlobalStatus.KAFKA_TOPIC_FIT_PENALTY], groupId = GlobalStatus.KAFKA_GROUP_ID)
    fun fitPenaltyListener(fitPenaltyId: String) {
        logger?.info(
            "KafkaListener FitPenaltyEvent with fitPenaltyListener start - fit penalty id = {}",
            fitPenaltyId
        )
        val saveFitPenaltyCommand =
            FitPenaltyDtoMapper.saveFitPenaltyRequestToCommand(fitPenaltyId, "kafka")
        saveFitPenaltyUseCase.saveFitPenalty(saveFitPenaltyCommand)
    }
}