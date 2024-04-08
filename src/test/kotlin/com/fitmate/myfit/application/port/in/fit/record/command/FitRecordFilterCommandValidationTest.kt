package com.fitmate.myfit.application.port.`in`.fit.record.command

import com.fitmate.myfit.application.port.`in`.fit.record.command.FitRecordFilterCommand
import jakarta.validation.ConstraintViolationException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.Instant

class FitRecordFilterCommandValidationTest {

    private val userId = "testUserId"
    private val recordStartDate = Instant.now()
    private val recordEndDate = recordStartDate.plusSeconds(100000)

    @Test
    @DisplayName("[단위][Command Validation] Filter Fit record Command validation success - 성공 테스트")
    fun `filter fit record command validation success test`() {
        //given when then
        Assertions.assertDoesNotThrow {
            FitRecordFilterCommand(
                userId,
                recordStartDate,
                recordEndDate
            )
        }
    }

    @Test
    @DisplayName("[단위][Command Validation] Filter Fit record Command user id validation fail - 실패 테스트")
    fun `filter fit record command user id validation fail test`() {
        //given when then
        Assertions.assertThrows(ConstraintViolationException::class.java) {
            FitRecordFilterCommand(
                "",
                recordStartDate,
                recordEndDate
            )
        }
    }
}