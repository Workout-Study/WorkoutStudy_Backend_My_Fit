package com.fitmate.myfit.application.service.adapter

import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordMultiMediaEndPointEntity
import com.fitmate.myfit.adapter.out.persistence.repository.FitRecordMultiMediaEndPointRepository
import com.fitmate.myfit.application.port.out.RegisterRecordMultiMediaEndPoint
import com.fitmate.myfit.domain.FitRecordMultiMediaEndPoint
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class RecordMultiMediaEndPointPersistenceAdapter(
    private val fitRecordMultiMediaEndPointRepository: FitRecordMultiMediaEndPointRepository
) :
    RegisterRecordMultiMediaEndPoint {

    @Transactional
    override fun saveAll(multiMediaEndPoints: List<FitRecordMultiMediaEndPoint>) {
        multiMediaEndPoints.forEach {
            fitRecordMultiMediaEndPointRepository.save(
                FitRecordMultiMediaEndPointEntity.domainToEntity(it)
            )
        }
    }
}