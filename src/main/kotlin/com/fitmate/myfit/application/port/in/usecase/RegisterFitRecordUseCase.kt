package com.fitmate.myfit.application.port.`in`.usecase

import com.fitmate.myfit.application.port.`in`.command.RegisterFitRecordCommand
import com.fitmate.myfit.application.port.`in`.response.RegisterFitRecordResponseDto

interface RegisterFitRecordUseCase {

    /**
     * Register fit record use case,
     * register fit record data to persistence
     *
     * @param registerFitRecordCommand data about register fit record with user id
     * @return Boolean about register success
     */
    fun registerFitRecord(registerFitRecordCommand: RegisterFitRecordCommand): RegisterFitRecordResponseDto
}