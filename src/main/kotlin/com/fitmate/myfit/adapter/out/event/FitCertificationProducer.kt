package com.fitmate.myfit.adapter.out.event

import com.fitmate.myfit.application.port.out.certification.ReadFitCertificationPort
import com.fitmate.myfit.common.GlobalStatus
import com.fitmate.myfit.common.exceptions.ResourceNotFoundException
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class FitCertificationProducer(
    private val readFitCertificationPort: ReadFitCertificationPort,
    private val fitCertificationKafkaTemplate: KafkaTemplate<String, String>
) {

    @Transactional(readOnly = true)
    fun produceFitCertificationEvent(fitCertificationId: Long) {
        val fitCertification = readFitCertificationPort.findById(fitCertificationId)
            .orElseThrow { ResourceNotFoundException("produce fit certification not found") }

        fitCertificationKafkaTemplate.send(GlobalStatus.KAFKA_TOPIC_FIT_CERTIFICATION, fitCertification.id.toString())
    }
}