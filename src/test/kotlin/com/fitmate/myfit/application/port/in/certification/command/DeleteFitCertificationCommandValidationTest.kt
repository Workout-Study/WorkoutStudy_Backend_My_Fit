package com.fitmate.myfit.application.port.`in`.certification.command

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class DeleteFitCertificationCommandValidationTest {

    private val requestUserId = 642
    private val fitCertificationId = 137L

    @Test
    @DisplayName("[단위][Command Validation] Delete Fit certification Command validation success - 성공 테스트")
    fun `delete fit certification command validation success test`() {
        //given when then
        Assertions.assertDoesNotThrow {
            DeleteFitCertificationCommand(
                fitCertificationId,
                requestUserId
            )
        }
    }
}