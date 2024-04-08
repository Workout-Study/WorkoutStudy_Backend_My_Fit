package com.fitmate.myfit.application.port.`in`.fit.record.command

import com.fitmate.myfit.application.port.`in`.fit.record.command.RegisterFitRecordCommand
import jakarta.validation.ConstraintViolationException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource
import java.time.Instant

class RegisterFitRecordCommandValidationTest {

    private val requestUserId = "testUserId"
    private val recordStartDate = Instant.now()
    private val recordEndDate = recordStartDate.plusSeconds(100000)
    private val multiMediaEndPoint: List<String> = listOf("https://avatars.githubusercontent.com/u/105261146?v=4")

    @Test
    @DisplayName("[단위][Command Validation] Register Fit record Command validation success - 성공 테스트")
    fun `register fit record command validation success test`() {
        //given when then
        Assertions.assertDoesNotThrow {
            RegisterFitRecordCommand(
                requestUserId,
                recordStartDate,
                recordEndDate,
                multiMediaEndPoint
            )
        }
    }

    @Test
    @DisplayName("[단위][Command Validation] Register Fit record Command with null multi media end point validation success - 성공 테스트")
    fun `register fit record command null multi media end point validation success test`() {
        //given when then
        Assertions.assertDoesNotThrow {
            RegisterFitRecordCommand(
                requestUserId,
                recordStartDate,
                recordEndDate,
                null
            )
        }
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Command Validation] Register Fit record Command empty user id Validation fail - 실패 테스트")
    fun `register fit record command empty user id validation fail test`(testUserId: String) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException::class.java) {
            RegisterFitRecordCommand(
                testUserId,
                recordStartDate,
                recordEndDate,
                multiMediaEndPoint
            )
        }
    }
}