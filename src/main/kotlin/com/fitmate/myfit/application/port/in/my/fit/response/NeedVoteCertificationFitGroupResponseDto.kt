package com.fitmate.myfit.application.port.`in`.my.fit.response

data class NeedVoteCertificationFitGroupResponseDto(
    val fitGroupId: Long,
    val fitGroupName: String,
    val needVoteCertificationList: List<NeedVoteCertificationResponseDto>
)
