package com.fitmate.myfit.adapter.`in`.event.consumer

import com.fitmate.myfit.adapter.`in`.web.certification.mapper.FitCertificationDtoMapper
import com.fitmate.myfit.application.port.`in`.certification.usecase.UpdateFitCertificationResultUseCase
import com.fitmate.myfit.common.GlobalStatus
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class FitCertificationResultConsumer(
    private val updateFitCertificationResultUseCase: UpdateFitCertificationResultUseCase
) {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(FitCertificationResultConsumer::class.java)
    }

    /**
     * kafka fit certification result event listener inbound
     *
     * @param fitCertificationId fit certification id where an event occurred
     */
    @KafkaListener(topics = [GlobalStatus.KAFKA_TOPIC_FIT_CERTIFICATION_RESULT], groupId = GlobalStatus.KAFKA_GROUP_ID)
    fun fitCertificationResultListener(fitCertificationId: String) {
        logger?.info(
            "KafkaListener FitCertificationResultEvent with fitCertificationResultListener start - fit certification id = {}",
            fitCertificationId
        )
        val updateFitCertificationResultCommand =
            FitCertificationDtoMapper.fitCertificationResultRequestToCommand(fitCertificationId, "kafka")
        updateFitCertificationResultUseCase.updateFitCertificationResult(updateFitCertificationResultCommand)
    }
}