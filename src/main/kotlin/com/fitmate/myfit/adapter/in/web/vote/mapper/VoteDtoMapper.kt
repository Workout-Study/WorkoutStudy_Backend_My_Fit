package com.fitmate.myfit.adapter.`in`.web.vote.mapper

import com.fitmate.myfit.adapter.`in`.web.vote.request.RegisterVoteRequest
import com.fitmate.myfit.adapter.`in`.web.vote.response.RegisterVoteResponse
import com.fitmate.myfit.application.port.`in`.vote.command.RegisterVoteCommand
import com.fitmate.myfit.application.port.`in`.vote.response.RegisterVoteResponseDto

class VoteDtoMapper {
    companion object {
        fun registerRequestToCommand(registerVoteRequest: RegisterVoteRequest): RegisterVoteCommand =
            RegisterVoteCommand(
                registerVoteRequest.requestUserId,
                registerVoteRequest.agree,
                registerVoteRequest.targetCategory,
                registerVoteRequest.targetId
            )

        fun dtoToRegisterResponse(registerVoteResponseDto: RegisterVoteResponseDto): RegisterVoteResponse =
            RegisterVoteResponse(registerVoteResponseDto.isRegisterSuccess)
    }
}