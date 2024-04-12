package com.fitmate.myfit.application.service.adapter

import com.fitmate.myfit.adapter.out.persistence.dto.FitCertificationWithVoteDto
import com.fitmate.myfit.adapter.out.persistence.entity.FitCertificationEntity
import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.adapter.out.persistence.repository.FitCertificationRepository
import com.fitmate.myfit.application.port.`in`.my.fit.response.FitCertificationDetailResponseDto
import com.fitmate.myfit.application.port.out.certification.ReadFitCertificationPort
import com.fitmate.myfit.application.port.out.certification.RegisterFitCertificationPort
import com.fitmate.myfit.application.port.out.certification.UpdateFitCertificationPort
import com.fitmate.myfit.common.GlobalStatus
import com.fitmate.myfit.domain.CertificationStatus
import com.fitmate.myfit.domain.FitCertification
import com.fitmate.myfit.domain.FitRecord
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Component
class FitCertificationPersistenceAdapter(
    private val fitCertificationRepository: FitCertificationRepository
) : ReadFitCertificationPort, RegisterFitCertificationPort, UpdateFitCertificationPort {

    @Transactional
    override fun registerFitCertification(fitCertification: FitCertification): FitCertification {
        val fitCertificationEntity = FitCertificationEntity.domainToEntity(fitCertification)
        val savedFitCertificationEntity = fitCertificationRepository.save(fitCertificationEntity)
        return FitCertification.entityToDomain(savedFitCertificationEntity)
    }

    @Transactional(readOnly = true)
    override fun findByFitRecordAndFitGroupIdAndCertificationStatusNot(
        fitRecord: FitRecord,
        fitGroupId: Long,
        certificationStatus: CertificationStatus
    ): List<FitCertification> {
        val fitRecordEntity = FitRecordEntity.domainToEntity(fitRecord)

        val fitCertificationEntityList =
            fitCertificationRepository.findByFitRecordEntityAndFitGroupIdAndCertificationStatusNotAndState(
                fitRecordEntity,
                fitGroupId,
                certificationStatus,
                GlobalStatus.PERSISTENCE_NOT_DELETED
            )

        return if (fitCertificationEntityList.isEmpty()) {
            emptyList()
        } else fitCertificationEntityList.map { FitCertification.entityToDomain(it) }
    }

    @Transactional(readOnly = true)
    override fun findByUserIdAndFitGroupIdAndCertificationStatus(
        userId: String,
        fitGroupId: Long,
        certificationStatus: CertificationStatus
    ): Optional<FitCertification> {
        val fitCertificationEntityOpt =
            fitCertificationRepository.findByUserIdAndFitGroupIdAndCertificationStatusAndState(
                userId,
                fitGroupId,
                certificationStatus,
                GlobalStatus.PERSISTENCE_NOT_DELETED
            )

        return if (fitCertificationEntityOpt.isPresent) {
            Optional.of(
                FitCertification.entityToDomain(fitCertificationEntityOpt.get())
            )
        } else Optional.empty()
    }

    @Transactional(readOnly = true)
    override fun findById(fitCertificationId: Long): Optional<FitCertification> {
        val fitCertificationEntityOpt = fitCertificationRepository.findById(fitCertificationId)
        return if (fitCertificationEntityOpt.isPresent) Optional.of(
            FitCertification.entityToDomain(
                fitCertificationEntityOpt.get()
            )
        )
        else Optional.empty()
    }

    @Transactional(readOnly = true)
    override fun findByFitRecordAndCertificationStatusNot(
        fitRecord: FitRecord,
        certificationStatus: CertificationStatus
    ): List<FitCertification> {
        val fitRecordEntity = FitRecordEntity.domainToEntity(fitRecord)

        val fitCertificationEntityList =
            fitCertificationRepository.findByFitRecordEntityAndCertificationStatusNotAndState(
                fitRecordEntity,
                certificationStatus,
                GlobalStatus.PERSISTENCE_NOT_DELETED
            )

        return if (fitCertificationEntityList.isEmpty()) {
            emptyList()
        } else fitCertificationEntityList.map { FitCertification.entityToDomain(it) }
    }

    @Transactional
    override fun updateFitCertification(fitCertification: FitCertification) {
        val fitCertificationEntity = FitCertificationEntity.domainToEntity(fitCertification)
        fitCertificationRepository.save(fitCertificationEntity)
    }

    @Transactional(readOnly = true)
    override fun countByUserIdAndFitGroupIdAndCertificationStatusAndDateGreaterThanEqual(
        userId: String,
        fitGroupId: Long,
        certified: CertificationStatus,
        instant: Instant
    ): Int =
        fitCertificationRepository
            .countByUserIdAndFitGroupIdAndCertificationStatusAndCreatedAtGreaterThanEqual(
                userId,
                fitGroupId,
                certified,
                instant
            )

    @Transactional(readOnly = true)
    override fun findNeedToVoteCertificationByFitGroupIdAndUserId(
        fitGroupId: Long,
        userId: String
    ): List<FitCertificationWithVoteDto> =
        fitCertificationRepository.findNeedToVoteCertificationByFitGroupIdAndUserId(fitGroupId, userId)

    @Transactional(readOnly = true)
    override fun findFitCertificationProgressDetailsByGroupId(
        fitGroupId: Long,
        requestUserId: String
    ): List<FitCertificationDetailResponseDto> =
        fitCertificationRepository.findFitCertificationProgressDetailsByGroupId(fitGroupId, requestUserId)
            .map {
                FitCertificationDetailResponseDto(
                    it.certificationId,
                    it.recordId,
                    it.certificationRequestUserId,
                    it.isUserVoteDone,
                    it.isUserAgree,
                    it.agreeCount.toInt(),
                    it.disagreeCount.toInt(),
                    it.maxAgreeCount.toInt(),
                    it.fitRecordStartDate,
                    it.fitRecordEndDate,
                    it.createdAt.plus(12, ChronoUnit.HOURS)
                )
            }
}