package com.fitmate.myfit.application.service.service

import com.fitmate.myfit.application.port.`in`.fit.group.command.SaveFitGroupForReadCommand
import com.fitmate.myfit.application.port.`in`.fit.group.response.SaveFitGroupForReadResponseDto
import com.fitmate.myfit.application.port.`in`.fit.group.usecase.RegisterFitGroupUseCase
import com.fitmate.myfit.application.port.out.fit.group.ReadFitGroupForReadPort
import com.fitmate.myfit.application.port.out.fit.group.SaveFitGroupForReadPort
import com.fitmate.myfit.application.service.converter.FitGroupForReadUseCaseConverter
import com.fitmate.myfit.domain.FitGroupForRead
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FitGroupForReadService(
    private val readFitGroupForReadPort: ReadFitGroupForReadPort,
    private val saveFitGroupForReadPort: SaveFitGroupForReadPort,
) : RegisterFitGroupUseCase {

    /**
     * register fit group data if not exist.
     * if exist update fit group data
     *
     * @param saveFitGroupForReadCommand data about fit group
     */
    @Transactional
    override fun saveFitGroupForRead(
        saveFitGroupForReadCommand: SaveFitGroupForReadCommand
    ): SaveFitGroupForReadResponseDto {
        val fitGroupForRead = readFitGroupForReadPort.findByFitGroupId(saveFitGroupForReadCommand.fitGroupId)
            .orElse(FitGroupForRead.createByCommand(saveFitGroupForReadCommand))

        fitGroupForRead.updateByCommand(saveFitGroupForReadCommand)
        val savedFitGroupForRead = saveFitGroupForReadPort.saveFitGroupForRead(fitGroupForRead)

        return FitGroupForReadUseCaseConverter.resultToSaveResponseDto(savedFitGroupForRead.id != null)
    }
}