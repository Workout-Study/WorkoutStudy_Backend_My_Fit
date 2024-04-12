package com.fitmate.myfit.application.port.`in`.my.fit.command

data class FitCertificationFilterByGroupCommand(
    val fitGroupId: Long,
    val requestUserId: String
)
