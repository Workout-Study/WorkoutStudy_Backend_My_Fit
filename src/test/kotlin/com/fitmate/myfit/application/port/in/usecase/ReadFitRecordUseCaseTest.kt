package com.fitmate.myfit.application.port.`in`.usecase

import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.application.port.`in`.command.FitRecordFilterCommand
import com.fitmate.myfit.application.port.`in`.command.FitRecordSliceFilterCommand
import com.fitmate.myfit.application.port.out.ReadFitRecordPort
import com.fitmate.myfit.application.port.out.ReadRecordMultiMediaEndPointPort
import com.fitmate.myfit.application.port.out.RegisterFitRecordPort
import com.fitmate.myfit.application.port.out.RegisterRecordMultiMediaEndPointPort
import com.fitmate.myfit.application.service.service.FitRecordService
import com.fitmate.myfit.domain.FitRecord
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.data.domain.Pageable
import java.time.Instant

@ExtendWith(MockitoExtension::class)
class ReadFitRecordUseCaseTest {

    @InjectMocks
    private lateinit var readFitRecordUseCase: FitRecordService

    @Mock
    private lateinit var readFitRecordPort: ReadFitRecordPort

    @Mock
    private lateinit var registerFitRecordPort: RegisterFitRecordPort

    @Mock
    private lateinit var registerRecordMultiMediaEndPointPort: RegisterRecordMultiMediaEndPointPort

    @Mock
    private lateinit var readRecordMultiMediaEndPointPort: ReadRecordMultiMediaEndPointPort

    private val userId = "testUserId"
    private val recordStartDate = Instant.now()
    private val recordEndDate = recordStartDate.plusSeconds(100000)
    private val multiMediaEndPoint: List<String> = listOf("https://avatars.githubusercontent.com/u/105261146?v=4")

    @Test
    @DisplayName("[단위][Use Case] Filter fit record - 성공 테스트")
    fun `register fit record service success test`() {
        //given
        val fitRecordFilterCommand = FitRecordFilterCommand(
            userId,
            recordStartDate,
            recordEndDate
        )

        val fitRecordList = mutableListOf<FitRecord>()

        for (i in 1..4) {
            val fitRecordEntity = FitRecordEntity(
                userId,
                recordStartDate,
                recordEndDate
            )

            fitRecordEntity.id = i.toLong()

            fitRecordList.add(
                FitRecord.entityToDomain(
                    fitRecordEntity
                )
            )
        }

        whenever(readFitRecordPort.filterFitRecord(any<FitRecordFilterCommand>())).thenReturn(fitRecordList)
        //when then
        Assertions.assertDoesNotThrow { readFitRecordUseCase.filterFitRecord(fitRecordFilterCommand) }
    }

    @Test
    @DisplayName("[단위][Use Case] Filter fit record empty list - 성공 테스트")
    fun `filter fit record service empty list success test`() {
        //given
        val fitRecordFilterCommand = FitRecordFilterCommand(
            userId,
            recordStartDate,
            recordEndDate
        )

        whenever(readFitRecordPort.filterFitRecord(any<FitRecordFilterCommand>())).thenReturn(emptyList())
        //when then
        Assertions.assertDoesNotThrow { readFitRecordUseCase.filterFitRecord(fitRecordFilterCommand) }.also {
            readFitRecordUseCase.filterFitRecord(fitRecordFilterCommand).isEmpty()
        }
    }

    @Test
    @DisplayName("[단위][Use Case] Slice Filter fit record - 성공 테스트")
    fun `slice register fit record service success test`() {
        //given
        val fitRecordSliceFilterCommand = FitRecordSliceFilterCommand(
            userId,
            recordStartDate,
            recordEndDate,
            Pageable.ofSize(2)
        )

        val fitRecordList = mutableListOf<FitRecord>()

        for (i in 1..4) {
            val fitRecordEntity = FitRecordEntity(
                userId,
                recordStartDate,
                recordEndDate
            )

            fitRecordEntity.id = i.toLong()

            fitRecordList.add(
                FitRecord.entityToDomain(
                    fitRecordEntity
                )
            )
        }

        whenever(readFitRecordPort.sliceFilterFitRecord(any<FitRecordSliceFilterCommand>())).thenReturn(fitRecordList)
        //when then
        Assertions.assertDoesNotThrow { readFitRecordUseCase.sliceFilterFitRecord(fitRecordSliceFilterCommand) }
            .also {
                Assertions.assertTrue(
                    readFitRecordUseCase.sliceFilterFitRecord(fitRecordSliceFilterCommand).hasNext()
                )
            }
    }

    @Test
    @DisplayName("[단위][Use Case] Slice Filter fit record empty list - 성공 테스트")
    fun `slice filter fit record service empty list success test`() {
        //given
        val fitRecordSliceFilterCommand = FitRecordSliceFilterCommand(
            userId,
            recordStartDate,
            recordEndDate,
            Pageable.ofSize(2)
        )

        whenever(readFitRecordPort.sliceFilterFitRecord(any<FitRecordSliceFilterCommand>())).thenReturn(emptyList())
        //when then
        Assertions.assertDoesNotThrow { readFitRecordUseCase.sliceFilterFitRecord(fitRecordSliceFilterCommand) }
            .also {
                Assertions.assertFalse(
                    readFitRecordUseCase.sliceFilterFitRecord(fitRecordSliceFilterCommand).hasNext()
                )
            }
    }
}