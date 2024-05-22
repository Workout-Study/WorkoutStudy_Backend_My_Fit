package com.fitmate.myfit.domain

import com.fitmate.myfit.adapter.out.persistence.entity.FitPenaltyEntity
import com.fitmate.myfit.application.port.`in`.fit.penalty.command.RegisterFitPenaltyCommand
import com.fitmate.myfit.application.service.dto.FitPenaltyDetailResponseDto
import com.fitmate.myfit.common.GlobalStatus
import java.time.Instant

class FitPenalty(
    val id: Long?,
    var fitPenaltyId: Long,
    var fitGroupId: Long,
    var userId: Int,
    var amount: Int,
    var paid: Boolean,
    var noNeedPay: Boolean,
    createUser: String
) : BaseDomain(GlobalStatus.PERSISTENCE_NOT_DELETED, Instant.now(), createUser) {
    fun updateByFitPenaltyDetail(fitPenaltyDetail: FitPenaltyDetailResponseDto, command: RegisterFitPenaltyCommand) {
        this.fitGroupId = fitPenaltyDetail.fitGroupId
        this.userId = fitPenaltyDetail.userId
        this.amount = fitPenaltyDetail.amount
        this.updatedAt = Instant.now()
        this.updateUser = command.eventPublisher
    }

    fun payPenalty(updateUserId: String) {
        this.paid = GlobalStatus.PENALTY_PAID
        this.updatedAt = Instant.now()
        this.updateUser = updateUserId
    }

    fun noNeedPayCheck(updateUserId: String) {
        this.noNeedPay = GlobalStatus.PENALTY_NO_NEED_PAY
        this.updatedAt = Instant.now()
        this.updateUser = updateUserId
    }

    companion object {
        fun entityToDomain(entity: FitPenaltyEntity): FitPenalty {
            val fitPenalty = FitPenalty(
                entity.id,
                entity.fitPenaltyId,
                entity.fitGroupId,
                entity.userId,
                entity.amount,
                entity.paid,
                entity.noNeedPay,
                entity.createUser
            )

            fitPenalty.setMetaDataByEntity(entity)

            return fitPenalty
        }

        fun createByFitPenaltyDetail(
            fitPenaltyDetail: FitPenaltyDetailResponseDto,
            command: RegisterFitPenaltyCommand
        ): FitPenalty = FitPenalty(
            null,
            fitPenaltyDetail.fitPenaltyId,
            fitPenaltyDetail.fitGroupId,
            fitPenaltyDetail.userId,
            fitPenaltyDetail.amount,
            GlobalStatus.PENALTY_NOT_PAID,
            GlobalStatus.PENALTY_NO_NEED_PAY,
            command.eventPublisher
        )
    }
}