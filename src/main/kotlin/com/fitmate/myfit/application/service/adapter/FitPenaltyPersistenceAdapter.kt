package com.fitmate.myfit.application.service.adapter

import com.fitmate.myfit.adapter.out.persistence.entity.FitPenaltyEntity
import com.fitmate.myfit.adapter.out.persistence.repository.FitPenaltyRepository
import com.fitmate.myfit.application.port.out.fit.penalty.ReadFitPenaltyPersistencePort
import com.fitmate.myfit.application.port.out.fit.penalty.SaveFitPenaltyPort
import com.fitmate.myfit.domain.FitPenalty
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class FitPenaltyPersistenceAdapter(
    private val fitPenaltyRepository: FitPenaltyRepository
) : ReadFitPenaltyPersistencePort, SaveFitPenaltyPort {

    @Transactional(readOnly = true)
    override fun findByFitPenaltyId(fitPenaltyId: Long): Optional<FitPenalty> {
        val fitPenaltyEntityOpt =
            fitPenaltyRepository.findByFitPenaltyId(fitPenaltyId)

        return if (fitPenaltyEntityOpt.isPresent) {
            Optional.of(
                FitPenalty.entityToDomain(fitPenaltyEntityOpt.get())
            )
        } else Optional.empty()
    }

    @Transactional
    override fun saveFitPenalty(fitPenalty: FitPenalty): FitPenalty {
        val fitPenaltyEntity = FitPenaltyEntity.domainToEntity(fitPenalty)
        val savedFitPenaltyEntity: FitPenaltyEntity = fitPenaltyRepository.save(fitPenaltyEntity)
        return FitPenalty.entityToDomain(savedFitPenaltyEntity)
    }
}