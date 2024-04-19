package com.fitmate.myfit.adapter.`in`.web.management.request

import jakarta.validation.constraints.NotEmpty

data class PaidFitPenaltyRequest(@field:NotEmpty val requestUserId: String)
