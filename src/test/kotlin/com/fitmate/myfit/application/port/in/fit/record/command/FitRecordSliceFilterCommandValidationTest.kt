package com.fitmate.myfit.application.port.`in`.fit.record.command

import com.fitmate.myfit.application.port.`in`.fit.record.command.FitRecordSliceFilterCommand
import jakarta.validation.ConstraintViolationException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Pageable
import java.time.Instant

class FitRecordSliceFilterCommandValidationTest {

    private val userId = "testUserId"
    private val recordStartDate = Instant.now()
    private val recordEndDate = recordStartDate.plusSeconds(100000)

    @Test
    @DisplayName("[단위][Command Validation] Slice Filter Fit record Command validation success - 성공 테스트")
    fun `slice filter fit record command validation success test`() {
        //given when then
        Assertions.assertDoesNotThrow {
            FitRecordSliceFilterCommand(
                userId,
                recordStartDate,
                recordEndDate,
                Pageable.ofSize(1)
            )
        }
    }

    @Test
    @DisplayName("[단위][Command Validation] Slice Filter Fit record Command user id validation fail - 실패 테스트")
    fun `slice filter fit record command user id validation fail test`() {
        //given when then
        Assertions.assertThrows(ConstraintViolationException::class.java) {
            FitRecordSliceFilterCommand(
                "",
                recordStartDate,
                recordEndDate,
                Pageable.ofSize(1)
            )
        }
    }
}