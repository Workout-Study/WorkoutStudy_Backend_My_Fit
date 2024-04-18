package com.fitmate.myfit.application.event

import com.fitmate.myfit.adapter.out.event.FitCertificationProducer
import com.fitmate.myfit.application.service.dto.event.DeleteFitCertificationEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class FitCertificationDeleteEventListener(
    private val fitCertificationProducer: FitCertificationProducer
) {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(FitCertificationDeleteEventListener::class.java)
    }

    /**
     * delete fit certification event register produce kafka event
     *
     * @param deleteFitCertificationEvent delete fit certification event
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    fun produceFitCertification(deleteFitCertificationEvent: DeleteFitCertificationEvent) {
        logger?.info(
            "DeleteFitCertificationEvent with produceFitCertification start - fit certification id = {}",
            deleteFitCertificationEvent.fitCertificationId
        )
        fitCertificationProducer.produceFitCertificationEvent(deleteFitCertificationEvent.fitCertificationId)
    }
}