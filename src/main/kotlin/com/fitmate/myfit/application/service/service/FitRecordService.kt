package com.fitmate.myfit.application.service.service

import com.fitmate.myfit.application.port.`in`.command.FitRecordFilterCommand
import com.fitmate.myfit.application.port.`in`.command.FitRecordSliceFilterCommand
import com.fitmate.myfit.application.port.`in`.command.RegisterFitRecordCommand
import com.fitmate.myfit.application.port.`in`.response.FitRecordDetailResponseDto
import com.fitmate.myfit.application.port.`in`.response.RegisterFitRecordResponseDto
import com.fitmate.myfit.application.port.`in`.usecase.ReadFitRecordUseCase
import com.fitmate.myfit.application.port.`in`.usecase.RegisterFitRecordUseCase
import com.fitmate.myfit.application.port.out.ReadFitRecordPort
import com.fitmate.myfit.application.port.out.ReadRecordMultiMediaEndPointPort
import com.fitmate.myfit.application.port.out.RegisterFitRecordPort
import com.fitmate.myfit.application.port.out.RegisterRecordMultiMediaEndPointPort
import com.fitmate.myfit.application.service.converter.FollowUseCaseConverter
import com.fitmate.myfit.domain.FitRecord
import com.fitmate.myfit.domain.FitRecordMultiMediaEndPoint
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FitRecordService(
    private val readFitRecordPort: ReadFitRecordPort,
    private val registerFitRecordPort: RegisterFitRecordPort,
    private val registerRecordMultiMediaEndPointPort: RegisterRecordMultiMediaEndPointPort,
    private val readRecordMultiMediaEndPoint: ReadRecordMultiMediaEndPointPort
) : RegisterFitRecordUseCase, ReadFitRecordUseCase {

    /**
     * Register fit record use case,
     * register fit record data to persistence
     *
     * @param registerFitRecordCommand data about register fit record with user id
     * @return Boolean about register success
     */
    @Transactional
    override fun registerFitRecord(registerFitRecordCommand: RegisterFitRecordCommand): RegisterFitRecordResponseDto {
        val fitRecord = FitRecord.createByCommand(registerFitRecordCommand)

        val savedFitRecord = registerFitRecordPort.registerFitRecord(fitRecord)

        registerFitRecordCommand.multiMediaEndPoints?.let {
            val fitRecordMultiMediaEndPoints = registerFitRecordCommand.multiMediaEndPoints.map {
                FitRecordMultiMediaEndPoint.createFitRecord(savedFitRecord, it)
            }

            registerRecordMultiMediaEndPointPort.saveAll(fitRecordMultiMediaEndPoints)
        }

        return FollowUseCaseConverter.fitRecordToRegisterResponseDto(savedFitRecord)
    }

    /**
     * Get Filtered fit record list use case,
     * Get Filtered fit record list data
     *
     * @param fitRecordFilterCommand filter condition
     * @return content and list data
     */
    @Transactional(readOnly = true)
    override fun filterFitRecord(fitRecordFilterCommand: FitRecordFilterCommand): List<FitRecordDetailResponseDto> {
        val fitRecordList = readFitRecordPort.filterFitRecord(fitRecordFilterCommand)

        return fitRecordList.map {
            FitRecordDetailResponseDto(
                it.id!!,
                it.recordStartDate,
                it.recordEndDate,
                it.createdAt,
                findMultiMediaEndPointsAndGet(it)
            )
        }.toList()
    }

    /**
     * Get Filtered fit record use case,
     * Get Filtered slice fit record data
     *
     * @param fitRecordSliceFilterCommand filter condition and pageable
     * @return content and slice data
     */
    @Transactional(readOnly = true)
    override fun sliceFilterFitRecord(fitRecordSliceFilterCommand: FitRecordSliceFilterCommand): Slice<FitRecordDetailResponseDto> {
        var fitRecordList = readFitRecordPort.sliceFilterFitRecord(fitRecordSliceFilterCommand)

        val hasNext: Boolean = fitRecordList.size > fitRecordSliceFilterCommand.pageable.pageSize

        if (hasNext) fitRecordList = fitRecordList.dropLast(1)

        val resultList = fitRecordList.map {
            FitRecordDetailResponseDto(
                it.id!!,
                it.recordStartDate,
                it.recordEndDate,
                it.createdAt,
                findMultiMediaEndPointsAndGet(it)
            )
        }.toList()

        return SliceImpl(resultList, fitRecordSliceFilterCommand.pageable, hasNext)
    }

    private fun findMultiMediaEndPointsAndGet(fitRecord: FitRecord): List<String> =
        readRecordMultiMediaEndPoint.findByFitRecordAndOrderByIdAsc(fitRecord).map { it.endPoint }
}