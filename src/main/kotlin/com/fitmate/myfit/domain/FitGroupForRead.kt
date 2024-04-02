package com.fitmate.myfit.domain

import com.fitmate.myfit.adapter.out.persistence.entity.FitGroupForReadEntity
import com.fitmate.myfit.application.port.`in`.fit.group.command.SaveFitGroupForReadCommand
import com.fitmate.myfit.application.service.dto.FitGroupResponseDto
import java.time.Instant

class FitGroupForRead private constructor(
    var id: Long?,
    val fitGroupId: Long,
    var fitGroupName: String,
    var cycle: Int,
    var frequency: Int,
    state: Boolean,
    createUser: String
) : BaseDomain(state, createdAt = Instant.now(), createUser) {

    fun updateByFitGroupDetail(dto: FitGroupResponseDto, command: SaveFitGroupForReadCommand) {
        this.fitGroupName = dto.fitGroupName
        this.cycle = dto.cycle
        this.frequency = dto.frequency
        this.state = dto.state
        this.updatedAt = Instant.now()
        this.updateUser = command.eventPublisher
    }

    companion object {

        fun entityToDomain(entity: FitGroupForReadEntity): FitGroupForRead {
            val fitGroupForRead = FitGroupForRead(
                entity.id,
                entity.fitGroupId,
                entity.fitGroupName,
                entity.cycle,
                entity.frequency,
                entity.state,
                entity.createUser
            )

            fitGroupForRead.setMetaDataByEntity(entity)

            return fitGroupForRead
        }

        fun createByFitGroupDetail(
            fitGroupDetail: FitGroupResponseDto,
            command: SaveFitGroupForReadCommand
        ): FitGroupForRead =
            FitGroupForRead(
                null,
                fitGroupDetail.fitGroupId,
                fitGroupDetail.fitGroupName,
                fitGroupDetail.cycle,
                fitGroupDetail.frequency,
                fitGroupDetail.state,
                command.eventPublisher
            )
    }
}
