package com.fitmate.myfit.application.port.`in`.vote.usecase

import com.fitmate.myfit.adapter.out.persistence.entity.VoteEntity
import com.fitmate.myfit.adapter.out.persistence.repository.VoteRepository
import com.fitmate.myfit.application.port.`in`.vote.command.RegisterVoteCommand
import com.fitmate.myfit.application.port.`in`.vote.command.UpdateVoteCommand
import com.fitmate.myfit.common.exceptions.ResourceNotFoundException
import com.fitmate.myfit.domain.Vote
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class UpdateVoteUseCaseBootTest {

    @Autowired
    private lateinit var updateVoteUseCase: UpdateVoteUseCase

    @Autowired
    private lateinit var voteRepository: VoteRepository

    private val requestUserId = "testUserId"
    private val agree = true
    private val targetCategory = 1
    private val targetId = 13L

    private lateinit var voteEntity: VoteEntity

    @BeforeEach
    fun setUp() {
        voteEntity = voteRepository.save(
            VoteEntity.domainToEntity(
                Vote.createByCommand(
                    RegisterVoteCommand(
                        requestUserId,
                        agree,
                        targetCategory,
                        targetId
                    )
                )
            )
        )
    }

    @Test
    @DisplayName("[통합][Use Case] Update vote - 성공 테스트")
    fun `update vote service success test`() {
        //given
        val updateVoteCommand = UpdateVoteCommand(
            requestUserId,
            agree,
            targetCategory,
            targetId
        )

        //when then
        var result = false

        Assertions.assertDoesNotThrow { result = updateVoteUseCase.updateVote(updateVoteCommand).isUpdateSuccess }
            .also { Assertions.assertTrue(result) }
    }

    @Test
    @DisplayName("[통합][Use Case] Update vote does not exist - 실패 테스트")
    fun `update vote service does not exist fail test`() {
        //given
        val updateVoteCommand = UpdateVoteCommand(
            requestUserId,
            agree,
            targetCategory,
            targetId
        )

        voteEntity.delete()
        voteRepository.save(voteEntity)

        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            updateVoteUseCase.updateVote(updateVoteCommand)
        }
    }
}