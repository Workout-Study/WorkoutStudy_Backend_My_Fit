package com.fitmate.myfit.application.port.out.certification

interface FitCertificationEventPublishPort {
    fun publishFitCertificationRegisterEvent(id: Long)
    fun publishFitCertificationDeleteEvent(id: Long)
}