package com.fitmate.myfit.application.port.`in`.vote.usecase

import com.fitmate.myfit.application.port.`in`.vote.command.UpdateVoteCommand
import com.fitmate.myfit.application.port.`in`.vote.response.UpdateVoteResponseDto

interface UpdateVoteUseCase {

    /**
     * Update vote use case,
     * update vote data to persistence
     *
     * @param updateVoteCommand data about update vote with user id
     * @return Boolean about update success
     */
    fun updateVote(updateVoteCommand: UpdateVoteCommand): UpdateVoteResponseDto
}