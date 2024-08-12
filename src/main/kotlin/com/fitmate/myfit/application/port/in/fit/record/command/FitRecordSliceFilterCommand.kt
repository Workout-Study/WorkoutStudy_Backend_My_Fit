package com.fitmate.myfit.application.port.`in`.fit.record.command

import com.fitmate.myfit.common.SelfValidating
import org.springframework.data.domain.Pageable

data class FitRecordSliceFilterCommand(
    val userId: Int,
    val recordEndStartDate: String? = null,
    val recordEndEndDate: String? = null,
    val pageable: Pageable,
) : SelfValidating<FitRecordSliceFilterCommand>() {

    init {
        this.validateSelf()
    }
}