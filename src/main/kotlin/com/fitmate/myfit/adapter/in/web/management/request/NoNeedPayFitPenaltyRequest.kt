package com.fitmate.myfit.adapter.`in`.web.management.request

import jakarta.validation.constraints.NotEmpty

data class NoNeedPayFitPenaltyRequest(@field:NotEmpty val requestUserId: String)