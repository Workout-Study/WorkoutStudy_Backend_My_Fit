package com.fitmate.myfit.application.event

import com.fitmate.myfit.adapter.out.event.FitCertificationProducer
import com.fitmate.myfit.application.service.dto.event.RegisterFitCertificationEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class FitCertificationRegisterEventListener(
    private val fitCertificationProducer: FitCertificationProducer
) {

    companion object {
        val logger: Logger? = LoggerFactory.getLogger(FitCertificationRegisterEventListener::class.java)
    }

    /**
     * register fit certification event register produce kafka event
     *
     * @param registerFitCertificationEvent register fit certification event
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    fun produceFitCertification(registerFitCertificationEvent: RegisterFitCertificationEvent) {
        logger?.info(
            "RegisterFitCertificationEvent with produceFitCertification start - fit certification id = {}",
            registerFitCertificationEvent.fitCertificationId
        )
        fitCertificationProducer.produceFitCertificationEvent(registerFitCertificationEvent.fitCertificationId)
    }
}