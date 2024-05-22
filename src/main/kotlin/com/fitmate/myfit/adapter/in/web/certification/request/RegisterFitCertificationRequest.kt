package com.fitmate.myfit.adapter.`in`.web.certification.request

data class RegisterFitCertificationRequest(
    val requestUserId: Int,
    val fitRecordId: Long,
    val fitGroupIds: List<Long>
)
