package com.fitmate.myfit.application.service.service

import com.fitmate.myfit.adapter.`in`.web.fit.record.response.FitCertificationResponse
import com.fitmate.myfit.application.port.`in`.fit.record.command.DeleteFitRecordCommand
import com.fitmate.myfit.application.port.`in`.fit.record.command.FitRecordFilterCommand
import com.fitmate.myfit.application.port.`in`.fit.record.command.FitRecordSliceFilterCommand
import com.fitmate.myfit.application.port.`in`.fit.record.command.RegisterFitRecordCommand
import com.fitmate.myfit.application.port.`in`.fit.record.response.DeleteFitRecordResponseDto
import com.fitmate.myfit.application.port.`in`.fit.record.response.FitRecordDetailResponseDto
import com.fitmate.myfit.application.port.`in`.fit.record.response.RegisterFitRecordResponseDto
import com.fitmate.myfit.application.port.`in`.fit.record.usecase.DeleteFitRecordUseCase
import com.fitmate.myfit.application.port.`in`.fit.record.usecase.ReadFitRecordUseCase
import com.fitmate.myfit.application.port.`in`.fit.record.usecase.RegisterFitRecordUseCase
import com.fitmate.myfit.application.port.out.certification.ReadFitCertificationPort
import com.fitmate.myfit.application.port.out.fit.group.ReadFitGroupForReadPort
import com.fitmate.myfit.application.port.out.fit.record.*
import com.fitmate.myfit.application.service.converter.FitRecordUseCaseConverter
import com.fitmate.myfit.common.exceptions.BadRequestException
import com.fitmate.myfit.common.exceptions.ResourceNotFoundException
import com.fitmate.myfit.domain.CertificationStatus
import com.fitmate.myfit.domain.FitRecord
import com.fitmate.myfit.domain.FitRecordMultiMediaEndPoint
import org.springframework.data.domain.Slice
import org.springframework.data.domain.SliceImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.temporal.ChronoUnit

@Service
class FitRecordService(
    private val readFitRecordPort: ReadFitRecordPort,
    private val registerFitRecordPort: RegisterFitRecordPort,
    private val registerRecordMultiMediaEndPointPort: RegisterRecordMultiMediaEndPointPort,
    private val readRecordMultiMediaEndPoint: ReadRecordMultiMediaEndPointPort,
    private val readFitCertificationPort: ReadFitCertificationPort,
    private val updateFitRecordPort: UpdateFitRecordPort,
    private val readFitGroupForReadPort: ReadFitGroupForReadPort
) : RegisterFitRecordUseCase, ReadFitRecordUseCase, DeleteFitRecordUseCase {

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

        return FitRecordUseCaseConverter.fitRecordToRegisterResponseDto(savedFitRecord)
    }

    /**
     * Delete fit record use case,
     * delete fit record data to persistence
     *
     * @param deleteFitRecordCommand data about delete fit record with user id
     * @return Boolean about delete success
     */
    @Transactional
    override fun deleteFitRecord(deleteFitRecordCommand: DeleteFitRecordCommand): DeleteFitRecordResponseDto {
        val fitRecord = readFitRecordPort.findById(deleteFitRecordCommand.fitRecordId)
            .orElseThrow { ResourceNotFoundException("fit record does not exist") }

        if (fitRecord.userId != deleteFitRecordCommand.requestUserId) throw BadRequestException("request user does not match fit record user id")

        if (fitRecord.isDeleted) throw BadRequestException("fit record already deleted")

        if (readFitCertificationPort.findByFitRecordAndCertificationStatusNot(fitRecord, CertificationStatus.REJECTED)
                .isNotEmpty()
        ) throw BadRequestException("fit record register on fit certification.")

        fitRecord.delete(deleteFitRecordCommand.requestUserId)
        updateFitRecordPort.updateFitRecord(fitRecord)

        return FitRecordUseCaseConverter.resultToDeleteResponseDto(fitRecord.isDeleted)
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
                findMultiMediaEndPointsAndGet(it),
                findFitCertifications(it)
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
                findMultiMediaEndPointsAndGet(it),
                findFitCertifications(it)
            )
        }.toList()

        return SliceImpl(resultList, fitRecordSliceFilterCommand.pageable, hasNext)
    }

    private fun findFitCertifications(fitRecord: FitRecord): List<FitCertificationResponse> {
        val fitCertifications = readFitCertificationPort.findByFitRecord(fitRecord)
        return fitCertifications.map {

            var fitGroupName = ""

            readFitGroupForReadPort.findByFitGroupId(it.fitGroupId)
                .ifPresentOrElse({ fitGroup ->
                    fitGroupName = fitGroup.fitGroupName
                }, { fitGroupName = "" })

            FitCertificationResponse(
                it.fitGroupId,
                fitGroupName,
                it.id!!,
                it.certificationStatus,
                it.createdAt,
                it.createdAt.plus(12, ChronoUnit.HOURS)
            )
        }
    }

    private fun findMultiMediaEndPointsAndGet(fitRecord: FitRecord): List<String> =
        readRecordMultiMediaEndPoint.findByFitRecordAndOrderByIdAsc(fitRecord).map { it.endPoint }
}