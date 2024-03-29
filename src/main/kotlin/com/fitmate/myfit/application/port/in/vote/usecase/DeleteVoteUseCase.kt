package com.fitmate.myfit.application.port.`in`.vote.usecase

import com.fitmate.myfit.application.port.`in`.vote.command.DeleteVoteCommand
import com.fitmate.myfit.application.port.`in`.vote.response.DeleteVoteResponseDto

interface DeleteVoteUseCase {

    /**
     * Delete vote use case,
     * delete vote data to persistence
     *
     * @param deleteVoteCommand data about delete vote with user id
     * @return Boolean about delete success
     */
    fun deleteVote(deleteVoteCommand: DeleteVoteCommand): DeleteVoteResponseDto
}