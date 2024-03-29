package com.fitmate.myfit.application.port.`in`.vote.command

import jakarta.validation.ConstraintViolationException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource

class RegisterVoteCommandValidationTest {

    private val requestUserId = "testUserId"
    private val agree = true
    private val targetCategory = 1
    private val targetId = 13L

    @Test
    @DisplayName("[단위][Command Validation] Register Vote Command validation success - 성공 테스트")
    fun `register vote command validation success test`() {
        //given when then
        Assertions.assertDoesNotThrow {
            RegisterVoteCommand(
                requestUserId,
                agree,
                targetCategory,
                targetId
            )
        }
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Command Validation] Register Vote Command empty user id Validation fail - 실패 테스트")
    fun `register vote command empty user id validation fail test`(testUserId: String) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException::class.java) {
            RegisterVoteCommand(
                testUserId,
                agree,
                targetCategory,
                targetId
            )
        }
    }
}