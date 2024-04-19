package com.fitmate.myfit.domain

import com.fitmate.myfit.adapter.out.persistence.entity.FitGroupForReadEntity
import com.fitmate.myfit.application.port.`in`.fit.group.command.SaveFitGroupForReadCommand
import com.fitmate.myfit.application.service.dto.FitGroupResponseDto
import java.time.Instant

class FitGroupForRead private constructor(
    var id: Long?,
    val fitGroupId: Long,
    var fitGroupName: String,
    var fitLeaderUserId: String,
    var cycle: Int,
    var frequency: Int,
    var thumbnailEndPoint: String?,
    state: Boolean,
    createUser: String
) : BaseDomain(state, createdAt = Instant.now(), createUser) {

    fun updateByFitGroupDetail(dto: FitGroupResponseDto, command: SaveFitGroupForReadCommand) {
        this.fitGroupName = dto.fitGroupName
        this.fitLeaderUserId = dto.fitLeaderUserId
        this.cycle = dto.cycle
        this.frequency = dto.frequency
        this.state = dto.state
        this.updatedAt = Instant.now()
        this.updateUser = command.eventPublisher

        if (dto.multiMediaEndPoints.isNotEmpty()) {
            this.thumbnailEndPoint = dto.multiMediaEndPoints[0]
        }
    }

    companion object {

        fun entityToDomain(entity: FitGroupForReadEntity): FitGroupForRead {
            val fitGroupForRead = FitGroupForRead(
                entity.id,
                entity.fitGroupId,
                entity.fitGroupName,
                entity.fitLeaderUserId,
                entity.cycle,
                entity.frequency,
                entity.thumbnailEndPoint,
                entity.state,
                entity.createUser
            )

            fitGroupForRead.setMetaDataByEntity(entity)

            return fitGroupForRead
        }

        fun createByFitGroupDetail(
            fitGroupDetail: FitGroupResponseDto,
            command: SaveFitGroupForReadCommand
        ): FitGroupForRead {
            var thumbnailEndPoint: String? = null
            if (fitGroupDetail.multiMediaEndPoints.isNotEmpty()) {
                thumbnailEndPoint = fitGroupDetail.multiMediaEndPoints[0]
            }

            return FitGroupForRead(
                null,
                fitGroupDetail.fitGroupId,
                fitGroupDetail.fitGroupName,
                fitGroupDetail.fitLeaderUserId,
                fitGroupDetail.cycle,
                fitGroupDetail.frequency,
                thumbnailEndPoint,
                fitGroupDetail.state,
                command.eventPublisher
            )
        }
    }
}
