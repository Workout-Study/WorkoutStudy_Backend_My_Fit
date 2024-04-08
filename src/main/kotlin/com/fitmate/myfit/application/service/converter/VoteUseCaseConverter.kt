package com.fitmate.myfit.application.service.converter

import com.fitmate.myfit.application.port.`in`.vote.response.DeleteVoteResponseDto
import com.fitmate.myfit.application.port.`in`.vote.response.RegisterVoteResponseDto
import com.fitmate.myfit.application.port.`in`.vote.response.UpdateVoteResponseDto
import com.fitmate.myfit.domain.Vote

class VoteUseCaseConverter {

    companion object {
        fun voteToRegisterResponseDto(vote: Vote): RegisterVoteResponseDto {
            return RegisterVoteResponseDto(vote.id != null)
        }

        fun resultToDeleteResponseDto(result: Boolean): DeleteVoteResponseDto =
            DeleteVoteResponseDto(result)

        fun resultToUpdateResponseDto(result: Boolean): UpdateVoteResponseDto =
            UpdateVoteResponseDto(result)
    }
}