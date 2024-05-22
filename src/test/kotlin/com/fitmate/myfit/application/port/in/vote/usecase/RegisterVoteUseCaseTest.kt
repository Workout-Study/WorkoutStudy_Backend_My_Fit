package com.fitmate.myfit.application.port.`in`.vote.usecase

import com.fitmate.myfit.adapter.out.persistence.entity.VoteEntity
import com.fitmate.myfit.application.port.`in`.vote.command.RegisterVoteCommand
import com.fitmate.myfit.application.port.out.vote.ReadVotePort
import com.fitmate.myfit.application.port.out.vote.RegisterVotePort
import com.fitmate.myfit.application.port.out.vote.UpdateVotePort
import com.fitmate.myfit.application.service.service.VoteService
import com.fitmate.myfit.common.exceptions.ResourceAlreadyExistException
import com.fitmate.myfit.domain.Vote
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.util.*

@ExtendWith(MockitoExtension::class)
class RegisterVoteUseCaseTest {

    @InjectMocks
    private lateinit var registerVoteUseCase: VoteService

    @Mock
    private lateinit var readVotePort: ReadVotePort

    @Mock
    private lateinit var registerVotePort: RegisterVotePort

    @Mock
    private lateinit var updateVotePort: UpdateVotePort

    private val requestUserId = 642
    private val agree = true
    private val targetCategory = 1
    private val targetId = 13L

    @Test
    @DisplayName("[단위][Use Case] Register vote - 성공 테스트")
    fun `register vote service success test`() {
        //given
        val registerVoteCommand = RegisterVoteCommand(
            requestUserId,
            agree,
            targetCategory,
            targetId
        )

        val voteEntity = VoteEntity.domainToEntity(Vote.createByCommand(registerVoteCommand))
        voteEntity.id = 137L

        whenever(registerVotePort.registerVote(any<Vote>())).thenReturn(Vote.entityToDomain(voteEntity))
        //when then
        Assertions.assertDoesNotThrow { registerVoteUseCase.registerVote(registerVoteCommand) }
            .also { Assertions.assertTrue(registerVoteUseCase.registerVote(registerVoteCommand).isRegisterSuccess) }
    }

    @Test
    @DisplayName("[단위][Use Case] Register vote already exist - 실패 테스트")
    fun `register vote service already exist fail test`() {
        //given
        val registerVoteCommand = RegisterVoteCommand(
            requestUserId,
            agree,
            targetCategory,
            targetId
        )

        val vote = Vote.createByCommand(registerVoteCommand)

        whenever(
            readVotePort.findByUserIdAndTargetCategoryAndTargetId(
                any<Int>(),
                any<Int>(),
                any<Long>()
            )
        ).thenReturn(Optional.of(vote))

        //when then
        Assertions.assertThrows(ResourceAlreadyExistException::class.java) {
            registerVoteUseCase.registerVote(registerVoteCommand)
        }
    }
}