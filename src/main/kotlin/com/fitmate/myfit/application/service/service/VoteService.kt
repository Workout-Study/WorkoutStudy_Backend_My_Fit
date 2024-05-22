package com.fitmate.myfit.application.service.service

import com.fitmate.myfit.application.port.`in`.vote.command.DeleteVoteCommand
import com.fitmate.myfit.application.port.`in`.vote.command.RegisterVoteCommand
import com.fitmate.myfit.application.port.`in`.vote.command.UpdateVoteCommand
import com.fitmate.myfit.application.port.`in`.vote.response.DeleteVoteResponseDto
import com.fitmate.myfit.application.port.`in`.vote.response.RegisterVoteResponseDto
import com.fitmate.myfit.application.port.`in`.vote.response.UpdateVoteResponseDto
import com.fitmate.myfit.application.port.`in`.vote.usecase.DeleteVoteUseCase
import com.fitmate.myfit.application.port.`in`.vote.usecase.RegisterVoteUseCase
import com.fitmate.myfit.application.port.`in`.vote.usecase.UpdateVoteUseCase
import com.fitmate.myfit.application.port.out.vote.ReadVotePort
import com.fitmate.myfit.application.port.out.vote.RegisterVotePort
import com.fitmate.myfit.application.port.out.vote.UpdateVotePort
import com.fitmate.myfit.application.service.converter.VoteUseCaseConverter
import com.fitmate.myfit.common.exceptions.BadRequestException
import com.fitmate.myfit.common.exceptions.ResourceAlreadyExistException
import com.fitmate.myfit.common.exceptions.ResourceNotFoundException
import com.fitmate.myfit.domain.Vote
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class VoteService(
    private val readVotePort: ReadVotePort,
    private val registerVotePort: RegisterVotePort,
    private val updateVotePort: UpdateVotePort
) : RegisterVoteUseCase, DeleteVoteUseCase, UpdateVoteUseCase {

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

        val vote = Vote.createByCommand(registerVoteCommand)

        val savedVote = registerVotePort.registerVote(vote)

        return VoteUseCaseConverter.voteToRegisterResponseDto(savedVote)
    }

    /**
     * Delete vote use case,
     * delete vote data to persistence
     *
     * @param deleteVoteCommand data about delete vote with user id
     * @return Boolean about delete success
     */
    @Transactional
    override fun deleteVote(deleteVoteCommand: DeleteVoteCommand): DeleteVoteResponseDto {
        val vote = readVotePort.findByUserIdAndTargetCategoryAndTargetId(
            deleteVoteCommand.requestUserId,
            deleteVoteCommand.targetCategory,
            deleteVoteCommand.targetId
        ).orElseThrow { throw ResourceNotFoundException("vote does not exist") }

        if (vote.isDeleted) throw BadRequestException("vote already deleted")

        vote.delete(deleteVoteCommand.requestUserId.toString())
        updateVotePort.updateVote(vote)

        return VoteUseCaseConverter.resultToDeleteResponseDto(vote.isDeleted)
    }

    /**
     * Update vote use case,
     * update vote data to persistence
     *
     * @param updateVoteCommand data about update vote with user id
     * @return Boolean about update success
     */
    @Transactional
    override fun updateVote(updateVoteCommand: UpdateVoteCommand): UpdateVoteResponseDto {
        val vote = readVotePort.findByUserIdAndTargetCategoryAndTargetId(
            updateVoteCommand.requestUserId,
            updateVoteCommand.targetCategory,
            updateVoteCommand.targetId
        ).orElseThrow { throw ResourceNotFoundException("vote does not exist") }

        if (vote.isDeleted) throw BadRequestException("vote already deleted")

        vote.updateAgree(updateVoteCommand)
        updateVotePort.updateVote(vote)

        return VoteUseCaseConverter.resultToUpdateResponseDto(true)
    }
}