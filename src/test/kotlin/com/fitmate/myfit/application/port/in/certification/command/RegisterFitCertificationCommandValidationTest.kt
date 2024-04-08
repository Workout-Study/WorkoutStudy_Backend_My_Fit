package com.fitmate.myfit.application.port.`in`.certification.command

import jakarta.validation.ConstraintViolationException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource

class RegisterFitCertificationCommandValidationTest {

    private val requestUserId = "testUserId"
    private val fitCertificationId = 137L
    private val fitGroupIds = listOf(13L, 7L, 2L)

    @Test
    @DisplayName("[단위][Command Validation] Register Fit certification Command validation success - 성공 테스트")
    fun `register fit certification command validation success test`() {
        //given when then
        Assertions.assertDoesNotThrow {
            RegisterFitCertificationCommand(
                requestUserId,
                fitCertificationId,
                fitGroupIds
            )
        }
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Command Validation] Register Fit certification Command empty user id Validation fail - 실패 테스트")
    fun `register fit certification command empty user id validation fail test`(testUserId: String) {
        //given when then
        Assertions.assertThrows(ConstraintViolationException::class.java) {
            RegisterFitCertificationCommand(
                testUserId,
                fitCertificationId,
                fitGroupIds
            )
        }
    }
}