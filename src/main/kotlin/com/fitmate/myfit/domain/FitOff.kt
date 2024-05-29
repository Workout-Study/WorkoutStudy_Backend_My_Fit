package com.fitmate.myfit.domain

import com.fitmate.myfit.adapter.out.persistence.entity.FitOffEntity
import com.fitmate.myfit.application.port.`in`.fit.off.command.RegisterFitOffCommand
import com.fitmate.myfit.application.port.`in`.fit.off.command.UpdateFitOffCommand
import com.fitmate.myfit.common.GlobalStatus
import java.time.Instant

class FitOff private constructor(
    val id: Long?,
    val userId: Int,
    var fitOffStartDate: Instant,
    var fitOffEndDate: Instant,
    var fitOffReason: String
) : BaseDomain(GlobalStatus.PERSISTENCE_NOT_DELETED, createdAt = Instant.now(), createUser = userId.toString()) {
    fun updateByCommand(updateFitOffCommand: UpdateFitOffCommand) {
        this.fitOffStartDate = updateFitOffCommand.fitOffStartDate
        this.fitOffEndDate = updateFitOffCommand.fitOffEndDate
        this.fitOffReason = updateFitOffCommand.fitOffReason
        this.updatedAt = Instant.now()
        this.updateUser = updateFitOffCommand.requestUserId.toString()
    }

    companion object {
        fun createByCommand(command: RegisterFitOffCommand): FitOff {
            return FitOff(
                null,
                command.requestUserId,
                command.fitOffStartDate,
                command.fitOffEndDate,
                command.fitOffReason
            )
        }

        fun entityToDomain(entity: FitOffEntity): FitOff {
            val fitOff = FitOff(
                entity.id,
                entity.userId,
                entity.fitOffStartDate,
                entity.fitOffEndDate,
                entity.fitOffReason
            )

            fitOff.setMetaDataByEntity(entity)

            return fitOff
        }
    }
}