package com.fitmate.myfit.domain

import com.fitmate.myfit.adapter.out.persistence.entity.FitGroupForReadEntity
import com.fitmate.myfit.application.port.`in`.fit.group.command.SaveFitGroupForReadCommand
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

    fun updateByCommand(command: SaveFitGroupForReadCommand) {
        this.fitGroupName = command.fitGroupName
        this.cycle = command.cycle
        this.frequency = command.frequency
        this.state = command.state
        this.updatedAt = Instant.now()
        this.updateUser = command.eventPublisher
    }

    companion object {

        fun createByCommand(command: SaveFitGroupForReadCommand): FitGroupForRead =
            FitGroupForRead(
                null,
                command.fitGroupId,
                command.fitGroupName,
                command.cycle,
                command.frequency,
                command.state,
                command.eventPublisher
            )

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
    }
}
