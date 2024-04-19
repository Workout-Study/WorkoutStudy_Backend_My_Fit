package com.fitmate.myfit.application.port.out.fit.penalty

import com.fitmate.myfit.application.port.`in`.fit.penalty.command.FitPenaltyFilterByFitGroupCommand
import com.fitmate.myfit.application.port.`in`.fit.penalty.command.FitPenaltyFilterByUserCommand
import com.fitmate.myfit.domain.FitPenalty
import org.springframework.data.domain.Pageable
import java.util.*

interface ReadFitPenaltyPersistencePort {
    fun findByFitPenaltyId(fitPenaltyId: Long): Optional<FitPenalty>
    fun sumAmountByUserIdAndCondition(command: FitPenaltyFilterByUserCommand): Int
    fun findByUserIdAndCondition(command: FitPenaltyFilterByUserCommand, pageable: Pageable): List<FitPenalty>
    fun sumAmountByFitGroupIdAndCondition(command: FitPenaltyFilterByFitGroupCommand): Int
    fun findByFitGroupIdAndCondition(
        command: FitPenaltyFilterByFitGroupCommand,
        pageable: Pageable
    ): List<FitPenalty>
}