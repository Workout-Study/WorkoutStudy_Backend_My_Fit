package com.fitmate.myfit.application.service.adapter

import com.fitmate.myfit.application.port.out.certification.FitCertificationEventPublishPort
import com.fitmate.myfit.application.service.dto.event.DeleteFitCertificationEvent
import com.fitmate.myfit.application.service.dto.event.RegisterFitCertificationEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class EventPublisherAdapter(private val eventPublisher: ApplicationEventPublisher) : FitCertificationEventPublishPort {

    override fun publishFitCertificationRegisterEvent(id: Long) {
        eventPublisher.publishEvent(RegisterFitCertificationEvent(id))
    }

    override fun publishFitCertificationDeleteEvent(id: Long) {
        eventPublisher.publishEvent(DeleteFitCertificationEvent(id))
    }
}