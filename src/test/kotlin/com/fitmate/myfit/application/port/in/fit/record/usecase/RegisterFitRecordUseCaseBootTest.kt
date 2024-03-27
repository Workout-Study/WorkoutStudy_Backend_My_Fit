package com.fitmate.myfit.application.port.`in`.fit.record.usecase

import com.fitmate.myfit.application.port.`in`.fit.record.command.RegisterFitRecordCommand
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@SpringBootTest
@Transactional
class RegisterFitRecordUseCaseBootTest {

    @Autowired
    private lateinit var registerFitRecordUseCase: RegisterFitRecordUseCase

    private val requestUserId = "testUserId"
    private val recordStartDate = Instant.now()
    private val recordEndDate = recordStartDate.plusSeconds(100000)
    private val multiMediaEndPoint: List<String> = listOf("https://avatars.githubusercontent.com/u/105261146?v=4")

    @Test
    @DisplayName("[통합][Use Case] Register fit record - 성공 테스트")
    fun `register fit record service success test`() {
        //given
        val registerFitRecordCommand = RegisterFitRecordCommand(
            requestUserId,
            recordStartDate,
            recordEndDate,
            multiMediaEndPoint
        )

        //when then
        Assertions.assertDoesNotThrow { registerFitRecordUseCase.registerFitRecord(registerFitRecordCommand) }
    }

    @Test
    @DisplayName("[통합][Use Case] Register fit record with empty multi media end points - 성공 테스트")
    fun `register fit record service with empty multi media end points success test`() {
        //given
        val registerFitRecordCommand = RegisterFitRecordCommand(
            requestUserId,
            recordStartDate,
            recordEndDate,
            null
        )

        //when then
        Assertions.assertDoesNotThrow { registerFitRecordUseCase.registerFitRecord(registerFitRecordCommand) }
    }
}