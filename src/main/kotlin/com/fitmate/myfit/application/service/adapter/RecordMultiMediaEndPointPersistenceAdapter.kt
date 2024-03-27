package com.fitmate.myfit.application.service.adapter

import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordMultiMediaEndPointEntity
import com.fitmate.myfit.adapter.out.persistence.repository.FitRecordMultiMediaEndPointRepository
import com.fitmate.myfit.application.port.out.ReadRecordMultiMediaEndPointPort
import com.fitmate.myfit.application.port.out.RegisterRecordMultiMediaEndPointPort
import com.fitmate.myfit.common.GlobalStatus
import com.fitmate.myfit.domain.FitRecord
import com.fitmate.myfit.domain.FitRecordMultiMediaEndPoint
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class RecordMultiMediaEndPointPersistenceAdapter(
    private val fitRecordMultiMediaEndPointRepository: FitRecordMultiMediaEndPointRepository
) : RegisterRecordMultiMediaEndPointPort, ReadRecordMultiMediaEndPointPort {

    @Transactional
    override fun saveAll(multiMediaEndPoints: List<FitRecordMultiMediaEndPoint>) {
        multiMediaEndPoints.forEach {
            fitRecordMultiMediaEndPointRepository.save(
                FitRecordMultiMediaEndPointEntity.domainToEntity(it)
            )
        }
    }

    @Transactional(readOnly = true)
    override fun findByFitRecordAndOrderByIdAsc(fitRecord: FitRecord): List<FitRecordMultiMediaEndPoint> {
        val multiMediaEndpoints = fitRecordMultiMediaEndPointRepository.findByFitRecordEntityAndStateOrderByIdAsc(
            FitRecordEntity.domainToEntity(fitRecord),
            GlobalStatus.PERSISTENCE_NOT_DELETED
        )
        return multiMediaEndpoints.map { FitRecordMultiMediaEndPoint.entityToDomain(it) }
    }
}