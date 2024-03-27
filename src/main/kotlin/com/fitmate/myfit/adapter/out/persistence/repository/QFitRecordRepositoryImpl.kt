package com.fitmate.myfit.adapter.out.persistence.repository

import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.adapter.out.persistence.entity.QFitRecordEntity.fitRecordEntity
import com.fitmate.myfit.application.port.`in`.fit.record.command.FitRecordFilterCommand
import com.fitmate.myfit.application.port.`in`.fit.record.command.FitRecordSliceFilterCommand
import com.fitmate.myfit.common.SliceUtil
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Repository
class QFitRecordRepositoryImpl(jpaQueryFactory: JPAQueryFactory) :
    QuerydslRepositorySupport(FitRecordEntity::class.java),
    QFitRecordRepository {

    private var factory: JPAQueryFactory = jpaQueryFactory

    @Transactional(readOnly = true)
    override fun sliceByCommand(fitRecordSliceFilterCommand: FitRecordSliceFilterCommand): List<FitRecordEntity> =
        factory.select(fitRecordEntity)
            .from(fitRecordEntity)
            .where(
                conditionWithUserId(fitRecordSliceFilterCommand.userId),
                conditionWithRecordDate(
                    fitRecordSliceFilterCommand.recordEndStartDate,
                    fitRecordSliceFilterCommand.recordEndEndDate
                )
            )
            .offset(fitRecordSliceFilterCommand.pageable.offset)
            .limit(SliceUtil.getSliceLimit(fitRecordSliceFilterCommand.pageable.pageSize))
            .orderBy(fitRecordEntity.id.desc())
            .fetch()

    @Transactional(readOnly = true)
    override fun filterByCommand(fitRecordFilterCommand: FitRecordFilterCommand): List<FitRecordEntity> =
        factory.select(fitRecordEntity)
            .from(fitRecordEntity)
            .where(
                conditionWithUserId(fitRecordFilterCommand.userId),
                conditionWithRecordDate(
                    fitRecordFilterCommand.recordEndStartDate,
                    fitRecordFilterCommand.recordEndEndDate
                )
            )
            .orderBy(fitRecordEntity.id.desc())
            .fetch()

    private fun conditionWithUserId(userId: String): Predicate {
        return fitRecordEntity.userId.eq(userId)
    }

    private fun conditionWithRecordDate(recordEndStartDate: Instant?, recordEndEndDate: Instant?): Predicate {
        val booleanBuilder = BooleanBuilder()

        if (recordEndStartDate != null) booleanBuilder.and(fitRecordEntity.recordEndDate.goe(recordEndStartDate))
        if (recordEndEndDate != null) booleanBuilder.and(fitRecordEntity.recordEndDate.loe(recordEndEndDate))

        return booleanBuilder
    }
}