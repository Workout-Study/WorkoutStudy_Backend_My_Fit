package com.fitmate.myfit.application.port.`in`.fit.record.usecase

import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.application.port.`in`.fit.record.command.RegisterFitRecordCommand
import com.fitmate.myfit.application.port.out.certification.ReadFitCertificationPort
import com.fitmate.myfit.application.port.out.fit.record.*
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
import java.time.Instant

@ExtendWith(MockitoExtension::class)
class RegisterFitRecordUseCaseTest {

    @InjectMocks
    private lateinit var registerFitRecordUseCase: FitRecordService

    @Mock
    private lateinit var readFitRecordPort: ReadFitRecordPort

    @Mock
    private lateinit var registerFitRecordPort: RegisterFitRecordPort

    @Mock
    private lateinit var registerRecordMultiMediaEndPointPort: RegisterRecordMultiMediaEndPointPort

    @Mock
    private lateinit var readRecordMultiMediaEndPointPort: ReadRecordMultiMediaEndPointPort

    @Mock
    private lateinit var readFitCertificationPort: ReadFitCertificationPort

    @Mock
    private lateinit var updateFitRecordPort: UpdateFitRecordPort

    private val requestUserId = "testUserId"
    private val recordStartDate = Instant.now()
    private val recordEndDate = recordStartDate.plusSeconds(100000)
    private val multiMediaEndPoint: List<String> = listOf("https://avatars.githubusercontent.com/u/105261146?v=4")

    @Test
    @DisplayName("[단위][Use Case] Register fit record - 성공 테스트")
    fun `register fit record service success test`() {
        //given
        val registerFitRecordCommand = RegisterFitRecordCommand(
            requestUserId,
            recordStartDate,
            recordEndDate,
            multiMediaEndPoint
        )

        val fitRecordEntity = FitRecordEntity(
            requestUserId,
            recordStartDate,
            recordEndDate
        )
        fitRecordEntity.id = 1225611L

        val fitRecord = FitRecord.entityToDomain(fitRecordEntity)

        whenever(registerFitRecordPort.registerFitRecord(any<FitRecord>())).thenReturn(fitRecord)
        //when then
        var result = false

        Assertions.assertDoesNotThrow {
            result = registerFitRecordUseCase.registerFitRecord(registerFitRecordCommand).isRegisterSuccess
        }
            .also { Assertions.assertTrue(result) }
    }

    @Test
    @DisplayName("[단위][Use Case] Register fit record with empty multi media end points - 성공 테스트")
    fun `register fit record service with empty multi media end points success test`() {
        //given
        val registerFitRecordCommand = RegisterFitRecordCommand(
            requestUserId,
            recordStartDate,
            recordEndDate,
            null
        )

        val fitRecordEntity = FitRecordEntity(
            requestUserId,
            recordStartDate,
            recordEndDate
        )
        fitRecordEntity.id = 122561L

        val fitRecord = FitRecord.entityToDomain(fitRecordEntity)

        whenever(registerFitRecordPort.registerFitRecord(any<FitRecord>())).thenReturn(fitRecord)
        //when then
        var result = false

        Assertions.assertDoesNotThrow {
            result = registerFitRecordUseCase.registerFitRecord(registerFitRecordCommand).isRegisterSuccess
        }
            .also { Assertions.assertTrue(result) }
    }
}