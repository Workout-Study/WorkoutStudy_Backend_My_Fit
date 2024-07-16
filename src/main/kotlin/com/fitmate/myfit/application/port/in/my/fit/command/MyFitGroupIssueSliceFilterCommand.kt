package com.fitmate.myfit.application.port.`in`.my.fit.command

import org.springframework.data.domain.Pageable

data class MyFitGroupIssueSliceFilterCommand(
    val userId: Int,
    val pageable: Pageable
)
