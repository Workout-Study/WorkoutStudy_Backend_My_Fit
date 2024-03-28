package com.fitmate.myfit.adapter.`in`.web.certification.request

import jakarta.validation.constraints.NotEmpty

data class DeleteFitCertificationRequest(@field:NotEmpty val requestUserId: String)
