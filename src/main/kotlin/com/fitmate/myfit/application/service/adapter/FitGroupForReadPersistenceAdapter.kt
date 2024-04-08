package com.fitmate.myfit.application.service.adapter

import com.fitmate.myfit.adapter.out.persistence.entity.FitGroupForReadEntity
import com.fitmate.myfit.adapter.out.persistence.repository.FitGroupForReadRepository
import com.fitmate.myfit.application.port.out.fit.group.ReadFitGroupForReadPort
import com.fitmate.myfit.application.port.out.fit.group.SaveFitGroupForReadPort
import com.fitmate.myfit.domain.FitGroupForRead
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class FitGroupForReadPersistenceAdapter(
    private val fitGroupForReadRepository: FitGroupForReadRepository
) : ReadFitGroupForReadPort, SaveFitGroupForReadPort {

    @Transactional(readOnly = true)
    override fun findByFitGroupId(fitGroupId: Long): Optional<FitGroupForRead> {
        val fitGroupForReadEntityOpt =
            fitGroupForReadRepository.findByFitGroupId(fitGroupId)

        return if (fitGroupForReadEntityOpt.isPresent) {
            Optional.of(
                FitGroupForRead.entityToDomain(fitGroupForReadEntityOpt.get())
            )
        } else Optional.empty()
    }

    @Transactional
    override fun saveFitGroupForRead(fitGroupForRead: FitGroupForRead): FitGroupForRead {
        val fitGroupForReadEntity = FitGroupForReadEntity.domainToEntity(fitGroupForRead)
        val savedFitGroupForReadEntity: FitGroupForReadEntity = fitGroupForReadRepository.save(fitGroupForReadEntity)
        return FitGroupForRead.entityToDomain(savedFitGroupForReadEntity)
    }
}