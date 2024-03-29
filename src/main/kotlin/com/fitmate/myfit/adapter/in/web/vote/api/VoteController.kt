package com.fitmate.myfit.adapter.`in`.web.vote.api

import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.vote.mapper.VoteDtoMapper
import com.fitmate.myfit.adapter.`in`.web.vote.request.RegisterVoteRequest
import com.fitmate.myfit.adapter.`in`.web.vote.response.RegisterVoteResponse
import com.fitmate.myfit.application.port.`in`.vote.usecase.RegisterVoteUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class VoteController(private val registerVoteUseCase: RegisterVoteUseCase) {

    /**
     * Register vote inbound adapter
     *
     * @param registerVoteRequest data about vote and user id
     * @return Boolean about register success
     */
    @PostMapping(GlobalURI.VOTE_ROOT)
    fun registerVote(@RequestBody @Valid registerVoteRequest: RegisterVoteRequest): ResponseEntity<RegisterVoteResponse> {
        val registerVoteCommand = VoteDtoMapper.registerRequestToCommand(registerVoteRequest)
        val registerVoteResponseDto = registerVoteUseCase.registerVote(registerVoteCommand)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(VoteDtoMapper.dtoToRegisterResponse(registerVoteResponseDto))
    }
}