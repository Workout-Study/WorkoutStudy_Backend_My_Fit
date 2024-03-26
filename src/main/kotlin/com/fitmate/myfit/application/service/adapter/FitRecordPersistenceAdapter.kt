package com.fitmate.myfit.application.service.adapter

import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.adapter.out.persistence.repository.FitRecordRepository
import com.fitmate.myfit.application.port.`in`.command.FitRecordFilterCommand
import com.fitmate.myfit.application.port.`in`.command.FitRecordSliceFilterCommand
import com.fitmate.myfit.application.port.out.ReadFitRecordPort
import com.fitmate.myfit.application.port.out.RegisterFitRecordPort
import com.fitmate.myfit.domain.FitRecord
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

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
}