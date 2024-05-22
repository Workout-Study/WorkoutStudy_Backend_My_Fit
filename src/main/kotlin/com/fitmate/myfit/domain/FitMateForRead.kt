package com.fitmate.myfit.domain

import com.fitmate.myfit.adapter.out.persistence.entity.FitMateForReadEntity
import com.fitmate.myfit.application.port.`in`.fit.mate.command.SaveFitMateForReadCommand
import com.fitmate.myfit.application.service.dto.FitMateResponseDto
import com.fitmate.myfit.common.GlobalStatus
import java.time.Instant

class FitMateForRead private constructor(
    var id: Long?,
    val fitGroupId: Long,
    val fitMateId: Long,
    val fitMateUserId: Int,
    state: Boolean,
    createUser: String
) : BaseDomain(state, createdAt = Instant.now(), createUser) {

    companion object {

        fun entityToDomain(entity: FitMateForReadEntity): FitMateForRead {
            val fitGroupForRead = FitMateForRead(
                entity.id,
                entity.fitGroupId,
                entity.fitMateId,
                entity.fitMateUserId,
                entity.state,
                entity.createUser
            )

            fitGroupForRead.setMetaDataByEntity(entity)

            return fitGroupForRead
        }

        fun createByDetail(
            fitGroupId: Long,
            dto: FitMateResponseDto,
            saveFitMateForReadCommand: SaveFitMateForReadCommand
        ): FitMateForRead {
            val fitMateForRead = FitMateForRead(
                null,
                fitGroupId,
                dto.fitMateId,
                dto.fitMateUserId,
                GlobalStatus.PERSISTENCE_NOT_DELETED,
                saveFitMateForReadCommand.eventPublisher
            )

            return fitMateForRead
        }
    }
}
