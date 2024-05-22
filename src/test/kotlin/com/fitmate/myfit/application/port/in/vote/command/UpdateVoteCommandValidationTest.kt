package com.fitmate.myfit.application.port.`in`.vote.command

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class UpdateVoteCommandValidationTest {

    private val requestUserId = 642
    private val agree = true
    private val targetCategory = 1
    private val targetId = 13L

    @Test
    @DisplayName("[단위][Command Validation] Update Vote Command validation success - 성공 테스트")
    fun `update vote command validation success test`() {
        //given when then
        Assertions.assertDoesNotThrow {
            UpdateVoteCommand(
                requestUserId,
                agree,
                targetCategory,
                targetId
            )
        }
    }
}