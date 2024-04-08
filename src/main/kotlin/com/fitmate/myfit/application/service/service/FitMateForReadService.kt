package com.fitmate.myfit.application.service.service

import com.fitmate.myfit.application.port.`in`.fit.mate.command.SaveFitMateForReadCommand
import com.fitmate.myfit.application.port.`in`.fit.mate.response.SaveFitMateForReadResponseDto
import com.fitmate.myfit.application.port.`in`.fit.mate.usecase.SaveFitMateUseCase
import com.fitmate.myfit.application.port.out.fit.mate.ReadFitMateForReadPort
import com.fitmate.myfit.application.port.out.fit.mate.ReadFitMatePort
import com.fitmate.myfit.application.port.out.fit.mate.SaveFitMateForReadPort
import com.fitmate.myfit.domain.FitMateForRead
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FitMateForReadService(
    private val readFitMateForReadPort: ReadFitMateForReadPort,
    private val saveFitMateForReadPort: SaveFitMateForReadPort,
    private val readFitMatePort: ReadFitMatePort
) : SaveFitMateUseCase {

    /**
     * register fit mate data if not exist.
     * if exist update fit mate data
     *
     * @param saveFitMateForReadCommand data about fit group
     */
    @Transactional
    override fun saveFitMateForRead(saveFitMateForReadCommand: SaveFitMateForReadCommand): SaveFitMateForReadResponseDto {
        val fitMateDetails = readFitMatePort.findByFitGroupId(saveFitMateForReadCommand.fitGroupId)
        val fitMateDetailList = fitMateDetails.fitMateDetails

        val fitMateForReadList = readFitMateForReadPort.findByFitGroupId(saveFitMateForReadCommand.fitGroupId)

        val fitMateDetailIdSet = fitMateDetailList.map { it.fitMateId }.toSet()
        val fitMateForReadIdSet = fitMateForReadList.map { it.fitMateId }.toSet()

        val newFitMateIds = fitMateDetailIdSet.subtract(fitMateForReadIdSet)
        val newFitMates = fitMateDetailList.filter { it.fitMateId in newFitMateIds }
            .map { FitMateForRead.createByDetail(fitMateDetails.fitGroupId, it, saveFitMateForReadCommand) }

        newFitMates.forEach { saveFitMateForReadPort.saveFitMate(it) }

        val deletedFitMateIds = fitMateForReadIdSet.subtract(fitMateDetailIdSet)
        val deletedFitMates = fitMateForReadList.filter { it.fitMateId in deletedFitMateIds }
            .map { it.delete(); it }

        deletedFitMates.forEach { saveFitMateForReadPort.saveFitMate(it) }

        return SaveFitMateForReadResponseDto(true)
    }
}