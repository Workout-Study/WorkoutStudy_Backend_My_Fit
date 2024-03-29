package com.fitmate.myfit.application.service.converter

import com.fitmate.myfit.application.port.`in`.vote.response.RegisterVoteResponseDto
import com.fitmate.myfit.domain.Vote

class VoteUseCaseConverter {

    companion object {
        fun voteToRegisterResponseDto(vote: Vote): RegisterVoteResponseDto {
            return RegisterVoteResponseDto(vote.id != null)
        }
    }
}