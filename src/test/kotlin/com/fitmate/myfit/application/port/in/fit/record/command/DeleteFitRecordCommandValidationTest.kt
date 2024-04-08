package com.fitmate.myfit.application.port.`in`.fit.record.command

import jakarta.validation.ConstraintViolationException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource

class DeleteFitRecordCommandValidationTest {

    private val fitRecordId = 675L
    private val requestUserId = "testUserId"

    @Test
    @DisplayName("[단위][Command Validation] Delete Fit record Command validation success - 성공 테스트")
    fun `delete fit record command validation success test`() {
        //given when then
        Assertions.assertDoesNotThrow {
            DeleteFitRecordCommand(
                fitRecordId,
                requestUserId
            )
        }
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Command Validation] Delete Fit record Command empty user id Validation fail - 실패 테스트")
    fun `delete fit record command empty user id validation fail test`(testUserId: String) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException::class.java) {
            DeleteFitRecordCommand(
                fitRecordId,
                testUserId
            )
        }
    }
}