package com.fitmate.myfit.adapter.out.persistence.repository

import com.fitmate.myfit.adapter.out.persistence.dto.FitCertificationDetailDto
import com.fitmate.myfit.adapter.out.persistence.dto.FitCertificationWithVoteDto
import com.fitmate.myfit.adapter.out.persistence.entity.FitCertificationEntity
import com.fitmate.myfit.adapter.out.persistence.entity.QFitCertificationEntity.fitCertificationEntity
import com.fitmate.myfit.adapter.out.persistence.entity.QFitMateForReadEntity.fitMateForReadEntity
import com.fitmate.myfit.adapter.out.persistence.entity.QFitRecordEntity.fitRecordEntity
import com.fitmate.myfit.adapter.out.persistence.entity.QVoteEntity.voteEntity
import com.fitmate.myfit.application.port.`in`.my.fit.command.MyFitGroupIssueSliceFilterCommand
import com.fitmate.myfit.common.GlobalStatus
import com.fitmate.myfit.common.SliceUtil
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.ExpressionUtils
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.Projections
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.Instant


@Repository
class QFitCertificationRepositoryImpl(jpaQueryFactory: JPAQueryFactory) :
    QuerydslRepositorySupport(FitCertificationEntity::class.java),
    QFitCertificationRepository {

    private var factory: JPAQueryFactory = jpaQueryFactory

    @Transactional(readOnly = true)
    override fun findNeedToVoteCertificationByFitGroupIdAndUserId(
        fitGroupId: Long,
        userId: Int
    ): List<FitCertificationWithVoteDto> =
        factory.select(
            Projections.constructor(
                FitCertificationWithVoteDto::class.java,
                fitCertificationEntity.id,
                fitCertificationEntity.userId,
                fitCertificationEntity.fitRecordEntity.id,
                fitCertificationEntity.certificationStatus,
                ExpressionUtils.`as`(
                    JPAExpressions
                        .select(voteEntity.count())
                        .from(voteEntity)
                        .where(
                            voteEntity.targetCategory.eq(GlobalStatus.VOTE_TARGET_CATEGORY_CERTIFICATION),
                            voteEntity.targetId.eq(fitCertificationEntity.id),
                            voteEntity.agree.eq(GlobalStatus.VOTE_AGREE),
                            voteEntity.state.eq(GlobalStatus.PERSISTENCE_NOT_DELETED)
                        ), "agreeCount"
                ),
                ExpressionUtils.`as`(
                    JPAExpressions
                        .select(voteEntity.count())
                        .from(voteEntity)
                        .where(
                            voteEntity.targetCategory.eq(GlobalStatus.VOTE_TARGET_CATEGORY_CERTIFICATION),
                            voteEntity.targetId.eq(fitCertificationEntity.id),
                            voteEntity.agree.eq(GlobalStatus.VOTE_DISAGREE),
                            voteEntity.state.eq(GlobalStatus.PERSISTENCE_NOT_DELETED)
                        ), "disagreeCount"
                ),
                ExpressionUtils.`as`(
                    JPAExpressions
                        .select(fitMateForReadEntity.count().subtract(1))
                        .from(fitMateForReadEntity)
                        .where(
                            fitMateForReadEntity.fitGroupId.eq(fitGroupId),
                            fitMateForReadEntity.state.eq(GlobalStatus.PERSISTENCE_NOT_DELETED)
                        ), "maxAgreeCount"
                ),
                fitCertificationEntity.createdAt
            ),
        ).from(fitCertificationEntity)
            .leftJoin(voteEntity)
            .on(
                voteEntity.targetCategory.eq(GlobalStatus.VOTE_TARGET_CATEGORY_CERTIFICATION),
                voteEntity.targetId.eq(fitCertificationEntity.id),
                voteEntity.userId.eq(userId),
                voteEntity.state.eq(GlobalStatus.PERSISTENCE_NOT_DELETED)
            )
            .where(
                fitCertificationEntity.fitGroupId.eq(fitGroupId),
                fitCertificationEntity.userId.ne(userId),
                fitCertificationEntity.state.eq(GlobalStatus.PERSISTENCE_NOT_DELETED),
                voteEntity.isNull
            )
            .fetch()

    @Transactional(readOnly = true)
    override fun findFitCertificationProgressDetailsByGroupId(
        fitGroupId: Long,
        requestUserId: Int
    ): List<FitCertificationDetailDto> =
        factory.select(
            Projections.constructor(
                FitCertificationDetailDto::class.java,
                fitCertificationEntity.id,
                fitCertificationEntity.fitRecordEntity.id,
                fitCertificationEntity.userId,
                ExpressionUtils.`as`(
                    JPAExpressions
                        .select(voteEntity.isNotNull)
                        .from(voteEntity)
                        .where(
                            voteEntity.targetCategory.eq(GlobalStatus.VOTE_TARGET_CATEGORY_CERTIFICATION),
                            voteEntity.targetId.eq(fitCertificationEntity.id),
                            voteEntity.userId.eq(requestUserId),
                            voteEntity.state.eq(GlobalStatus.PERSISTENCE_NOT_DELETED)
                        ), "isUserVoteDone"
                ),
                ExpressionUtils.`as`(
                    JPAExpressions
                        .select(voteEntity.isNotNull.and(voteEntity.agree.eq(GlobalStatus.VOTE_AGREE)))
                        .from(voteEntity)
                        .where(
                            voteEntity.targetCategory.eq(GlobalStatus.VOTE_TARGET_CATEGORY_CERTIFICATION),
                            voteEntity.targetId.eq(fitCertificationEntity.id),
                            voteEntity.userId.eq(requestUserId),
                            voteEntity.state.eq(GlobalStatus.PERSISTENCE_NOT_DELETED)
                        ), "isUserAgree"
                ),
                ExpressionUtils.`as`(
                    JPAExpressions
                        .select(voteEntity.count())
                        .from(voteEntity)
                        .where(
                            voteEntity.targetCategory.eq(GlobalStatus.VOTE_TARGET_CATEGORY_CERTIFICATION),
                            voteEntity.targetId.eq(fitCertificationEntity.id),
                            voteEntity.agree.eq(GlobalStatus.VOTE_AGREE),
                            voteEntity.state.eq(GlobalStatus.PERSISTENCE_NOT_DELETED)
                        ), "agreeCount"
                ),
                ExpressionUtils.`as`(
                    JPAExpressions
                        .select(voteEntity.count())
                        .from(voteEntity)
                        .where(
                            voteEntity.targetCategory.eq(GlobalStatus.VOTE_TARGET_CATEGORY_CERTIFICATION),
                            voteEntity.targetId.eq(fitCertificationEntity.id),
                            voteEntity.agree.eq(GlobalStatus.VOTE_DISAGREE),
                            voteEntity.state.eq(GlobalStatus.PERSISTENCE_NOT_DELETED)
                        ), "disagreeCount"
                ),
                ExpressionUtils.`as`(
                    JPAExpressions
                        .select(fitMateForReadEntity.count().subtract(1))
                        .from(fitMateForReadEntity)
                        .where(
                            fitMateForReadEntity.fitGroupId.eq(fitGroupId),
                            fitMateForReadEntity.state.eq(GlobalStatus.PERSISTENCE_NOT_DELETED)
                        ), "maxAgreeCount"
                ),
                fitRecordEntity.recordStartDate,
                fitRecordEntity.recordEndDate,
                fitCertificationEntity.createdAt
            )
        ).from(fitCertificationEntity)
            .leftJoin(fitRecordEntity)
            .on(fitCertificationEntity.fitRecordEntity.eq(fitRecordEntity))
            .where(
                fitCertificationEntity.fitGroupId.eq(fitGroupId),
                fitCertificationEntity.state.eq(GlobalStatus.PERSISTENCE_NOT_DELETED)
            )
            .fetch()

    @Transactional(readOnly = true)
    override fun findFitCertificationByFitGroupIssue(
        command: MyFitGroupIssueSliceFilterCommand,
        fitGroupIdList: List<Long>,
        fitGroupIssueStartDate: Instant,
        fitGroupIssueEndDate: Instant
    ): List<FitCertificationEntity> {
        return factory.select(fitCertificationEntity)
            .from(fitCertificationEntity)
            .where(
                fitCertificationEntity.state.eq(GlobalStatus.PERSISTENCE_NOT_DELETED),
                getFitGroupIdCondition(fitGroupIdList),
                getDateCondition(fitGroupIssueStartDate, fitGroupIssueEndDate)
            )
            .offset(command.pageable.offset)
            .limit(SliceUtil.getSliceLimit(command.pageable.pageSize))
            .orderBy(fitCertificationEntity.createdAt.desc())
            .fetch()
    }

    private fun getDateCondition(startDate: Instant, endDate: Instant): BooleanBuilder {
        val booleanBuilder = BooleanBuilder();

        booleanBuilder.and(fitCertificationEntity.createdAt.goe(startDate))
        booleanBuilder.and(fitCertificationEntity.createdAt.loe(endDate))

        return booleanBuilder
    }

    private fun getFitGroupIdCondition(fitGroupIdList: List<Long>): Predicate? {
        return fitCertificationEntity.fitGroupId.`in`(fitGroupIdList)
    }
}