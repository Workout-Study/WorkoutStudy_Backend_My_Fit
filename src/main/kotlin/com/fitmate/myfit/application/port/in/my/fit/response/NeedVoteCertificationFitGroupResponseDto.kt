package com.fitmate.myfit.application.port.`in`.my.fit.response

data class NeedVoteCertificationFitGroupResponseDto(
    val fitGroupId: Long,
    val needVoteCertificationList: List<NeedVoteCertificationResponseDto>
)
