package com.fitmate.myfit.application.service.service

import com.fitmate.myfit.application.port.`in`.vote.command.RegisterVoteCommand
import com.fitmate.myfit.application.port.`in`.vote.response.RegisterVoteResponseDto
import com.fitmate.myfit.application.port.`in`.vote.usecase.RegisterVoteUseCase
import com.fitmate.myfit.application.port.out.vote.ReadVotePort
import com.fitmate.myfit.application.port.out.vote.RegisterVotePort
import com.fitmate.myfit.application.service.converter.VoteUseCaseConverter
import com.fitmate.myfit.common.exceptions.ResourceAlreadyExistException
import com.fitmate.myfit.domain.Vote
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class VoteService(
    private val readVotePort: ReadVotePort,
    private val registerVotePort: RegisterVotePort
) : RegisterVoteUseCase {

    /**
     * Register vote use case,
     * register vote data to persistence
     *
     * @param registerVoteCommand data about register vote with user id
     * @return Boolean about register success
     */
    @Transactional
    override fun registerVote(registerVoteCommand: RegisterVoteCommand): RegisterVoteResponseDto {
        readVotePort.findByUserIdAndTargetCategoryAndTargetId(
            registerVoteCommand.requestUserId,
            registerVoteCommand.targetCategory,
            registerVoteCommand.targetId
        ).ifPresent { throw ResourceAlreadyExistException("vote already exist") }

        // TODO - Need to check fit mate

        val vote = Vote.createByCommand(registerVoteCommand)

        val savedVote = registerVotePort.registerVote(vote)

        return VoteUseCaseConverter.voteToRegisterResponseDto(savedVote)
    }
}