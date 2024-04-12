package com.fitmate.myfit.application.port.`in`.fit.record.response

data class RegisterFitRecordResponseDto(
    val isRegisterSuccess: Boolean,
    val fitRecordId: Long?
)
