package com.fitmate.myfit.adapter.`in`.web.fit.record.request

import jakarta.validation.constraints.NotEmpty

data class DeleteFitRecordRequest(@field:NotEmpty val requestUserId: String)
