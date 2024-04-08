package com.fitmate.myfit.application.port.`in`.vote.usecase

import com.fitmate.myfit.application.port.`in`.vote.command.RegisterVoteCommand
import com.fitmate.myfit.application.port.`in`.vote.response.RegisterVoteResponseDto

interface RegisterVoteUseCase {

    /**
     * Register vote use case,
     * register vote data to persistence
     *
     * @param registerVoteCommand data about register vote with user id
     * @return Boolean about register success
     */
    fun registerVote(registerVoteCommand: RegisterVoteCommand): RegisterVoteResponseDto
}