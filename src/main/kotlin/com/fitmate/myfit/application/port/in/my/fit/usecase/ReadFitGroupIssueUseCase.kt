package com.fitmate.myfit.application.port.`in`.my.fit.usecase

import com.fitmate.myfit.adapter.`in`.web.my.fit.response.MyFitGroupIssueSliceFilterResponse
import com.fitmate.myfit.application.port.`in`.my.fit.command.MyFitGroupIssueSliceFilterCommand

interface ReadFitGroupIssueUseCase {
    fun filterFitGroupIssue(command: MyFitGroupIssueSliceFilterCommand): MyFitGroupIssueSliceFilterResponse
}