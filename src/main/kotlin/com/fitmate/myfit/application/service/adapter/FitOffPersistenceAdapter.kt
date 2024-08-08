package com.fitmate.myfit.application.service.adapter

import com.fitmate.myfit.adapter.out.persistence.entity.FitOffEntity
import com.fitmate.myfit.adapter.out.persistence.repository.FitOffRepository
import com.fitmate.myfit.application.port.out.fit.off.ReadFitOffPort
import com.fitmate.myfit.application.port.out.fit.off.RegisterFitOffPort
import com.fitmate.myfit.application.port.out.fit.off.UpdateFitOffPort
import com.fitmate.myfit.common.GlobalStatus
import com.fitmate.myfit.domain.FitOff
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Component
class FitOffPersistenceAdapter(
    private val fitOffRepository: FitOffRepository
) : ReadFitOffPort, RegisterFitOffPort, UpdateFitOffPort {

    @Transactional
    override fun registerFitOff(fitOff: FitOff): FitOff {
        val fitOffEntity = FitOffEntity.domainToEntity(fitOff)
        val savedFitOffEntity: FitOffEntity = fitOffRepository.save(fitOffEntity)
        return FitOff.entityToDomain(savedFitOffEntity)
    }

    @Transactional(readOnly = true)
    override fun findById(fitOffId: Long): Optional<FitOff> {
        val fitOffEntityOpt = fitOffRepository.findByIdAndState(fitOffId, GlobalStatus.PERSISTENCE_NOT_DELETED)

        return if (fitOffEntityOpt.isPresent) {
            Optional.of(
                FitOff.entityToDomain(fitOffEntityOpt.get())
            )
        } else Optional.empty()
    }

    @Transactional(readOnly = true)
    override fun findProceedingFitOffByUserIds(userIdList: List<Int>): List<FitOff> {
        val now = Instant.now()

        val fitOffEntityList =
            fitOffRepository.findByUserIdInAndStateAndFitOffStartDateLessThanEqualAndFitOffEndDateGreaterThanEqual(
                userIdList,
                GlobalStatus.PERSISTENCE_NOT_DELETED,
                now,
                now
            )

        return fitOffEntityList.map { FitOff.entityToDomain(it) }.toList()
    }

    @Transactional
    override fun updateFitOff(fitOff: FitOff) {
        val fitOffEntity = FitOffEntity.domainToEntity(fitOff)
        fitOffRepository.save(fitOffEntity)
    }

    @Transactional(readOnly = true)
    override fun countByUserIdAndFitOffDate(userId: Int, fitOffStartDate: Instant, fitOffEndDate: Instant): Int {
        return fitOffRepository.countByUserIdAndFitOffStartDateBetweenAndFitOffEndDateBetweenAndState(
            userId,
            fitOffStartDate,
            fitOffEndDate,
            fitOffStartDate,
            fitOffEndDate,
            GlobalStatus.PERSISTENCE_NOT_DELETED
        )
    }
}