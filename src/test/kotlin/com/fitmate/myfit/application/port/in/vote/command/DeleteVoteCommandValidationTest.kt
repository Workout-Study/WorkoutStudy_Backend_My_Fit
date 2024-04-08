package com.fitmate.myfit.application.port.`in`.vote.command

import jakarta.validation.ConstraintViolationException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource

class DeleteVoteCommandValidationTest {

    private val requestUserId = "testUserId"
    private val targetCategory = 1
    private val targetId = 13L

    @Test
    @DisplayName("[단위][Command Validation] Delete Vote Command validation success - 성공 테스트")
    fun `delete vote command validation success test`() {
        //given when then
        Assertions.assertDoesNotThrow {
            DeleteVoteCommand(
                requestUserId,
                targetCategory,
                targetId
            )
        }
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Command Validation] Delete Vote Command empty user id Validation fail - 실패 테스트")
    fun `delete vote command empty user id validation fail test`(testUserId: String) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException::class.java) {
            DeleteVoteCommand(
                testUserId,
                targetCategory,
                targetId
            )
        }
    }
}