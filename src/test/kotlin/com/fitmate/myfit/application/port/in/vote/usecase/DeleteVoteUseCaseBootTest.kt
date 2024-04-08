package com.fitmate.myfit.application.port.`in`.vote.usecase

import com.fitmate.myfit.adapter.out.persistence.entity.VoteEntity
import com.fitmate.myfit.adapter.out.persistence.repository.VoteRepository
import com.fitmate.myfit.application.port.`in`.vote.command.DeleteVoteCommand
import com.fitmate.myfit.application.port.`in`.vote.command.RegisterVoteCommand
import com.fitmate.myfit.common.exceptions.ResourceNotFoundException
import com.fitmate.myfit.domain.Vote
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
@TestPropertySource(locations = ["classpath:application-test.yml"])
class DeleteVoteUseCaseBootTest {

    @Autowired
    private lateinit var deleteVoteUseCase: DeleteVoteUseCase

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
    @DisplayName("[통합][Use Case] Delete vote - 성공 테스트")
    fun `delete vote service success test`() {
        //given
        val deleteVoteCommand = DeleteVoteCommand(
            requestUserId,
            targetCategory,
            targetId
        )

        //when then
        var result = false

        Assertions.assertDoesNotThrow { result = deleteVoteUseCase.deleteVote(deleteVoteCommand).isDeleteSuccess }
            .also { Assertions.assertTrue(result) }
    }

    @Test
    @DisplayName("[통합][Use Case] Delete vote does not exist - 실패 테스트")
    fun `delete vote service does not exist fail test`() {
        //given
        val deleteVoteCommand = DeleteVoteCommand(
            requestUserId,
            targetCategory,
            targetId
        )

        voteEntity.delete()
        voteRepository.save(voteEntity)

        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            deleteVoteUseCase.deleteVote(deleteVoteCommand)
        }
    }
}