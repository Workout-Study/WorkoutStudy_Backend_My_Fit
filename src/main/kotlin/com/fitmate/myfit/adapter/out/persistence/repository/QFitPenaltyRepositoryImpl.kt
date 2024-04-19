package com.fitmate.myfit.adapter.out.persistence.repository

import com.fitmate.myfit.adapter.out.persistence.entity.FitPenaltyEntity
import com.fitmate.myfit.adapter.out.persistence.entity.QFitPenaltyEntity.fitPenaltyEntity
import com.fitmate.myfit.application.port.`in`.fit.penalty.command.FitPenaltyFilterByFitGroupCommand
import com.fitmate.myfit.application.port.`in`.fit.penalty.command.FitPenaltyFilterByUserCommand
import com.fitmate.myfit.common.GlobalStatus
import com.fitmate.myfit.common.SliceUtil
import com.fitmate.myfit.common.exceptions.BadRequestException
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import java.time.Instant

@Repository
class QFitPenaltyRepositoryImpl(
    jpaQueryFactory: JPAQueryFactory
) : QuerydslRepositorySupport(FitPenaltyEntity::class.java), QFitPenaltyRepository {

    private var factory: JPAQueryFactory = jpaQueryFactory

    @Transactional(readOnly = true)
    override fun sumAmountByUserIdAndCondition(command: FitPenaltyFilterByUserCommand): Int =
        factory.select(fitPenaltyEntity.amount.sum())
            .from(fitPenaltyEntity)
            .where(
                fitPenaltyEntity.state.eq(GlobalStatus.PERSISTENCE_NOT_DELETED),
                eqUserId(command.userId),
                fitGroupCondition(command.fitGroupId),
                dateCondition(command.startDate, command.endDate),
                paidCondition(command.onlyPaid, command.onlyNotPaid)
            )
            .fetchOne() ?: 0

    @Transactional(readOnly = true)
    override fun findByUserIdAndCondition(
        command: FitPenaltyFilterByUserCommand,
        pageable: Pageable
    ): List<FitPenaltyEntity> =
        factory.select(fitPenaltyEntity)
            .from(fitPenaltyEntity)
            .where(
                fitPenaltyEntity.state.eq(GlobalStatus.PERSISTENCE_NOT_DELETED),
                eqUserId(command.userId),
                fitGroupCondition(command.fitGroupId),
                dateCondition(command.startDate, command.endDate),
                paidCondition(command.onlyPaid, command.onlyNotPaid)
            )
            .offset(pageable.offset)
            .limit(SliceUtil.getSliceLimit(pageable.pageSize))
            .fetch()

    private fun eqUserId(userId: String?): BooleanExpression? =
        if (StringUtils.hasText(userId)) fitPenaltyEntity.userId.eq(userId)
        else null

    private fun fitGroupCondition(fitGroupId: Long?): BooleanExpression? =
        if (fitGroupId != null) fitPenaltyEntity.fitGroupId.eq(fitGroupId)
        else null

    private fun dateCondition(startDate: Instant, endDate: Instant): BooleanBuilder {
        val booleanBuilder = BooleanBuilder()

        booleanBuilder.and(fitPenaltyEntity.createdAt.goe(startDate))
        booleanBuilder.and(fitPenaltyEntity.createdAt.loe(endDate))

        return booleanBuilder
    }

    private fun paidCondition(onlyPaid: Boolean?, onlyNotPaid: Boolean?): BooleanExpression? {
        if (onlyPaid == true && onlyNotPaid == true) throw BadRequestException("only paid and only not paid can filter together")

        if (onlyPaid == true) return fitPenaltyEntity.paid.eq(GlobalStatus.PENALTY_PAID)
        if (onlyNotPaid == true) return fitPenaltyEntity.paid.eq(GlobalStatus.PENALTY_NOT_PAID)

        return null
    }

    @Transactional(readOnly = true)
    override fun sumAmountByFitGroupIdAndCondition(command: FitPenaltyFilterByFitGroupCommand): Int =
        factory.select(fitPenaltyEntity.amount.sum())
            .from(fitPenaltyEntity)
            .where(
                fitPenaltyEntity.state.eq(GlobalStatus.PERSISTENCE_NOT_DELETED),
                eqUserId(command.fitMateUserId),
                fitGroupCondition(command.fitGroupId),
                dateCondition(command.startDate, command.endDate),
                paidCondition(command.onlyPaid, command.onlyNotPaid)
            )
            .fetchOne() ?: 0

    @Transactional(readOnly = true)
    override fun findByFitGroupIdAndCondition(
        command: FitPenaltyFilterByFitGroupCommand,
        pageable: Pageable
    ): List<FitPenaltyEntity> =
        factory.select(fitPenaltyEntity)
            .from(fitPenaltyEntity)
            .where(
                fitPenaltyEntity.state.eq(GlobalStatus.PERSISTENCE_NOT_DELETED),
                eqUserId(command.fitMateUserId),
                fitGroupCondition(command.fitGroupId),
                dateCondition(command.startDate, command.endDate),
                paidCondition(command.onlyPaid, command.onlyNotPaid)
            )
            .offset(pageable.offset)
            .limit(SliceUtil.getSliceLimit(pageable.pageSize))
            .fetch()
}