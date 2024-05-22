package com.fitmate.myfit.application.port.`in`.certification.command

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class RegisterFitCertificationCommandValidationTest {

    private val requestUserId = 642
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
}