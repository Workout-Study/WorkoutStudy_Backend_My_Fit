package com.fitmate.myfit.application.port.`in`.fit.record.command

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.Instant

class RegisterFitRecordCommandValidationTest {

    private val requestUserId = 642
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
}