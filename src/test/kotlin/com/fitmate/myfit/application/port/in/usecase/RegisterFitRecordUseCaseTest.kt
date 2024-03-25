package com.fitmate.myfit.application.port.`in`.usecase

import com.fitmate.myfit.application.port.`in`.command.RegisterFitRecordCommand
import com.fitmate.myfit.application.port.out.ReadFitRecordPort
import com.fitmate.myfit.application.port.out.RegisterFitRecordPort
import com.fitmate.myfit.application.port.out.RegisterRecordMultiMediaEndPoint
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
    private lateinit var registerRecordMultiMediaEndPoint: RegisterRecordMultiMediaEndPoint

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

        val fitRecord = FitRecord.createByCommand(registerFitRecordCommand)

        whenever(registerFitRecordPort.registerFitRecord(any<FitRecord>())).thenReturn(fitRecord)
        //when then
        Assertions.assertDoesNotThrow { registerFitRecordUseCase.registerFitRecord(registerFitRecordCommand) }
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

        val fitRecord = FitRecord.createByCommand(registerFitRecordCommand)

        whenever(registerFitRecordPort.registerFitRecord(any<FitRecord>())).thenReturn(fitRecord)
        //when then
        Assertions.assertDoesNotThrow { registerFitRecordUseCase.registerFitRecord(registerFitRecordCommand) }
    }
}