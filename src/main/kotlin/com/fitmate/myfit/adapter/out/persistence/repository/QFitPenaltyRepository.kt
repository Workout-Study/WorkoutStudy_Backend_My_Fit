package com.fitmate.myfit.adapter.out.persistence.repository

import com.fitmate.myfit.adapter.out.persistence.entity.FitPenaltyEntity
import com.fitmate.myfit.application.port.`in`.fit.penalty.command.FitPenaltyFilterByFitGroupCommand
import com.fitmate.myfit.application.port.`in`.fit.penalty.command.FitPenaltyFilterByUserCommand
import org.springframework.data.domain.Pageable

interface QFitPenaltyRepository {
    fun sumAmountByUserIdAndCondition(command: FitPenaltyFilterByUserCommand): Int
    fun findByUserIdAndCondition(command: FitPenaltyFilterByUserCommand, pageable: Pageable): List<FitPenaltyEntity>
    fun sumAmountByFitGroupIdAndCondition(command: FitPenaltyFilterByFitGroupCommand): Int
    fun findByFitGroupIdAndCondition(
        command: FitPenaltyFilterByFitGroupCommand,
        pageable: Pageable
    ): List<FitPenaltyEntity>
}