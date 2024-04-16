package com.fitmate.myfit.application.service.adapter

import com.fitmate.myfit.adapter.out.persistence.entity.FitMateForReadEntity
import com.fitmate.myfit.adapter.out.persistence.repository.FitMateForReadRepository
import com.fitmate.myfit.application.port.out.fit.mate.ReadFitMateForReadPort
import com.fitmate.myfit.application.port.out.fit.mate.SaveFitMateForReadPort
import com.fitmate.myfit.common.GlobalStatus
import com.fitmate.myfit.domain.FitMateForRead
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class FitMateForReadPersistenceAdapter(
    private val fitMateForReadRepository: FitMateForReadRepository
) : ReadFitMateForReadPort, SaveFitMateForReadPort {

    @Transactional(readOnly = true)
    override fun findByFitGroupId(fitGroupId: Long): List<FitMateForRead> {
        val fitMateForReadEntityList =
            fitMateForReadRepository.findByFitGroupIdAndState(
                fitGroupId,
                GlobalStatus.PERSISTENCE_NOT_DELETED
            )

        return if (fitMateForReadEntityList.isEmpty()) {
            emptyList()
        } else fitMateForReadEntityList.map { FitMateForRead.entityToDomain(it) }
    }

    @Transactional
    override fun saveFitMate(fitMateForRead: FitMateForRead) {
        val fitMateForReadEntity = FitMateForReadEntity.domainToEntity(fitMateForRead)
        fitMateForReadRepository.save(fitMateForReadEntity)
    }

    @Transactional(readOnly = true)
    override fun findByFitMateUserId(userId: String): List<FitMateForRead> {
        val fitMateForReadEntityList =
            fitMateForReadRepository.findByFitMateUserIdAndState(
                userId,
                GlobalStatus.PERSISTENCE_NOT_DELETED
            )

        return if (fitMateForReadEntityList.isEmpty()) {
            emptyList()
        } else fitMateForReadEntityList.map { FitMateForRead.entityToDomain(it) }
    }

    @Transactional(readOnly = true)
    override fun countByFitGroup(fitGroupId: Long): Int =
        fitMateForReadRepository.countByFitGroupIdAndState(fitGroupId, GlobalStatus.PERSISTENCE_NOT_DELETED)
}