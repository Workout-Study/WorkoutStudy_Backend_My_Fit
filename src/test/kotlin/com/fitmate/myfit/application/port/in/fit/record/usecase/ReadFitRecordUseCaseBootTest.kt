package com.fitmate.myfit.application.port.`in`.fit.record.usecase

import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.adapter.out.persistence.repository.FitRecordMultiMediaEndPointRepository
import com.fitmate.myfit.adapter.out.persistence.repository.FitRecordRepository
import com.fitmate.myfit.application.port.`in`.fit.record.command.FitRecordFilterCommand
import com.fitmate.myfit.application.port.`in`.fit.record.command.FitRecordSliceFilterCommand
import com.fitmate.myfit.application.service.service.FitRecordService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Pageable
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@SpringBootTest
@Transactional
@TestPropertySource(locations = ["classpath:application-test.yml"])
class ReadFitRecordUseCaseBootTest {

    @Autowired
    private lateinit var readFitRecordUseCase: FitRecordService

    @Autowired
    private lateinit var fitRecordRepository: FitRecordRepository

    @Autowired
    private lateinit var multiMediaEndPointRepository: FitRecordMultiMediaEndPointRepository

    private val userId = "testUserId"
    private val recordStartDate = Instant.now()
    private val recordEndDate = recordStartDate.plusSeconds(100000)
    private val multiMediaEndPoint: List<String> = listOf("https://avatars.githubusercontent.com/u/105261146?v=4")

    @BeforeEach
    fun setUp() {
        for (i in 1..5) {
            fitRecordRepository.save(
                FitRecordEntity(
                    userId,
                    recordStartDate.minusSeconds((i * 50000).toLong()),
                    recordEndDate.minusSeconds((i * 100000).toLong())
                )
            )
        }
    }

    @Test
    @DisplayName("[단위][Use Case] Filter fit record - 성공 테스트")
    fun `register fit record service success test`() {
        //given
        val fitRecordFilterCommand = FitRecordFilterCommand(
            userId,
            recordStartDate,
            recordEndDate
        )

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

        fitRecordRepository.deleteAll()
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
            null,
            null,
            Pageable.ofSize(2)
        )

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

        fitRecordRepository.deleteAll()
        //when then
        Assertions.assertDoesNotThrow { readFitRecordUseCase.sliceFilterFitRecord(fitRecordSliceFilterCommand) }
            .also {
                Assertions.assertFalse(
                    readFitRecordUseCase.sliceFilterFitRecord(fitRecordSliceFilterCommand).hasNext()
                )
            }
    }
}