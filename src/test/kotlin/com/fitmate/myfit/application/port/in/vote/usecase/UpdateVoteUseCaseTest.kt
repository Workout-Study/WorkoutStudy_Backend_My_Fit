package com.fitmate.myfit.application.port.`in`.vote.usecase

import com.fitmate.myfit.application.port.`in`.vote.command.RegisterVoteCommand
import com.fitmate.myfit.application.port.`in`.vote.command.UpdateVoteCommand
import com.fitmate.myfit.application.port.out.vote.ReadVotePort
import com.fitmate.myfit.application.port.out.vote.RegisterVotePort
import com.fitmate.myfit.application.port.out.vote.UpdateVotePort
import com.fitmate.myfit.application.service.service.VoteService
import com.fitmate.myfit.common.exceptions.ResourceNotFoundException
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
class UpdateVoteUseCaseTest {

    @InjectMocks
    private lateinit var updateVoteUseCase: VoteService

    @Mock
    private lateinit var readVotePort: ReadVotePort

    @Mock
    private lateinit var registerVotePort: RegisterVotePort

    @Mock
    private lateinit var updateVotePort: UpdateVotePort

    private val requestUserId = "testUserId"
    private val agree = true
    private val targetCategory = 1
    private val targetId = 13L

    @Test
    @DisplayName("[단위][Use Case] Update vote - 성공 테스트")
    fun `update vote service success test`() {
        //given
        val updateVoteCommand = UpdateVoteCommand(
            requestUserId,
            agree,
            targetCategory,
            targetId
        )

        val registerVoteCommand = RegisterVoteCommand(
            requestUserId,
            agree,
            targetCategory,
            targetId
        )

        val vote = Vote.createByCommand(registerVoteCommand)

        whenever(
            readVotePort.findByUserIdAndTargetCategoryAndTargetId(
                any<String>(),
                any<Int>(),
                any<Long>()
            )
        ).thenReturn(Optional.of(vote))
        //when then
        var result = false

        Assertions.assertDoesNotThrow { result = updateVoteUseCase.updateVote(updateVoteCommand).isUpdateSuccess }
            .also { Assertions.assertTrue(result) }
    }

    @Test
    @DisplayName("[단위][Use Case] Update vote does not exist - 실패 테스트")
    fun `update vote service does not exist fail test`() {
        //given
        val updateVoteCommand = UpdateVoteCommand(
            requestUserId,
            agree,
            targetCategory,
            targetId
        )

        whenever(
            readVotePort.findByUserIdAndTargetCategoryAndTargetId(
                any<String>(),
                any<Int>(),
                any<Long>()
            )
        ).thenReturn(Optional.empty())
        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            updateVoteUseCase.updateVote(updateVoteCommand)
        }
    }
}