package com.fitmate.myfit.application.service.adapter

import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.adapter.out.persistence.repository.FitRecordRepository
import com.fitmate.myfit.application.port.`in`.fit.record.command.FitRecordFilterCommand
import com.fitmate.myfit.application.port.`in`.fit.record.command.FitRecordSliceFilterCommand
import com.fitmate.myfit.application.port.out.certification.ReadFitRecordPort
import com.fitmate.myfit.application.port.out.certification.RegisterFitRecordPort
import com.fitmate.myfit.common.GlobalStatus
import com.fitmate.myfit.domain.FitRecord
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class FitRecordPersistenceAdapter(
    private val fitRecordRepository: FitRecordRepository
) : ReadFitRecordPort, RegisterFitRecordPort {

    @Transactional
    override fun registerFitRecord(fitRecord: FitRecord): FitRecord {
        val fitRecordEntity = FitRecordEntity.domainToEntity(fitRecord)

        val savedFitRecordEntity: FitRecordEntity = fitRecordRepository.save(fitRecordEntity)

        return FitRecord.entityToDomain(savedFitRecordEntity)
    }

    @Transactional(readOnly = true)
    override fun sliceFilterFitRecord(fitRecordSliceFilterCommand: FitRecordSliceFilterCommand): List<FitRecord> =
        fitRecordRepository.sliceByCommand(fitRecordSliceFilterCommand).map(FitRecord::entityToDomain).toList()

    @Transactional(readOnly = true)
    override fun filterFitRecord(fitRecordFilterCommand: FitRecordFilterCommand): List<FitRecord> =
        fitRecordRepository.filterByCommand(fitRecordFilterCommand).map(FitRecord::entityToDomain).toList()

    @Transactional(readOnly = true)
    override fun findById(fitRecordId: Long): Optional<FitRecord> {
        val fitRecordEntityOpt = fitRecordRepository.findByIdAndState(fitRecordId, GlobalStatus.PERSISTENCE_NOT_DELETED)

        return if (fitRecordEntityOpt.isPresent) {
            Optional.of(
                FitRecord.entityToDomain(fitRecordEntityOpt.get())
            )
        } else Optional.empty()
    }
}