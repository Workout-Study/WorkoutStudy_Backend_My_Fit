package com.fitmate.myfit.application.port.`in`.vote.usecase

import com.fitmate.myfit.application.port.`in`.vote.command.RegisterVoteCommand
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class RegisterVoteUseCaseBootTest {

    @Autowired
    private lateinit var registerVoteUseCase: RegisterVoteUseCase

    private val requestUserId = "testUserId"
    private val agree = true
    private val targetCategory = 1
    private val targetId = 13L

    @Test
    @DisplayName("[통합][Use Case] Register vote - 성공 테스트")
    fun `register vote service success test`() {
        //given
        val registerVoteCommand = RegisterVoteCommand(
            requestUserId,
            agree,
            targetCategory,
            targetId
        )

        //when then
        Assertions.assertTrue(registerVoteUseCase.registerVote(registerVoteCommand).isRegisterSuccess)
    }
}