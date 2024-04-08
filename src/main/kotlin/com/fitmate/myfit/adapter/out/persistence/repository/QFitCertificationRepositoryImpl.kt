package com.fitmate.myfit.adapter.out.persistence.repository

import com.fitmate.myfit.adapter.out.persistence.dto.FitCertificationWithVoteDto
import com.fitmate.myfit.adapter.out.persistence.entity.FitCertificationEntity
import com.fitmate.myfit.adapter.out.persistence.entity.QFitCertificationEntity.fitCertificationEntity
import com.fitmate.myfit.adapter.out.persistence.entity.QFitMateForReadEntity.fitMateForReadEntity
import com.fitmate.myfit.adapter.out.persistence.entity.QVoteEntity.voteEntity
import com.fitmate.myfit.common.GlobalStatus
import com.querydsl.core.types.ExpressionUtils
import com.querydsl.core.types.Projections
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional


@Repository
class QFitCertificationRepositoryImpl(jpaQueryFactory: JPAQueryFactory) :
    QuerydslRepositorySupport(FitCertificationEntity::class.java),
    QFitCertificationRepository {

    private var factory: JPAQueryFactory = jpaQueryFactory

    @Transactional(readOnly = true)
    override fun findNeedToVoteCertificationByFitGroupIdAndUserId(
        fitGroupId: Long,
        userId: String
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
                        .select(fitMateForReadEntity.count())
                        .from(fitMateForReadEntity)
                        .where(
                            fitMateForReadEntity.fitGroupId.eq(fitGroupId),
                            fitMateForReadEntity.state.eq(GlobalStatus.PERSISTENCE_NOT_DELETED),
                            fitMateForReadEntity.fitMateUserId.ne(userId)
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
}