package com.fitmate.myfit.adapter.`in`.web.vote.api

import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.vote.mapper.VoteDtoMapper
import com.fitmate.myfit.adapter.`in`.web.vote.request.DeleteVoteRequest
import com.fitmate.myfit.adapter.`in`.web.vote.request.RegisterVoteRequest
import com.fitmate.myfit.adapter.`in`.web.vote.request.UpdateVoteRequest
import com.fitmate.myfit.adapter.`in`.web.vote.response.DeleteVoteResponse
import com.fitmate.myfit.adapter.`in`.web.vote.response.RegisterVoteResponse
import com.fitmate.myfit.adapter.`in`.web.vote.response.UpdateVoteResponse
import com.fitmate.myfit.application.port.`in`.vote.usecase.DeleteVoteUseCase
import com.fitmate.myfit.application.port.`in`.vote.usecase.RegisterVoteUseCase
import com.fitmate.myfit.application.port.`in`.vote.usecase.UpdateVoteUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class VoteController(
    private val registerVoteUseCase: RegisterVoteUseCase,
    private val deleteVoteUseCase: DeleteVoteUseCase,
    private val updateVoteUseCase: UpdateVoteUseCase
) {

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

    /**
     * Delete vote inbound adapter
     *
     * @param deleteVoteRequest request user id with target category and target id
     * @return Boolean about delete success
     */
    @DeleteMapping(GlobalURI.VOTE_ROOT)
    fun deleteVote(
        @RequestBody @Valid deleteVoteRequest: DeleteVoteRequest
    ): ResponseEntity<DeleteVoteResponse> {
        val deleteVoteCommand = VoteDtoMapper.deleteRequestToCommand(deleteVoteRequest)
        val deleteVoteResponseDto = deleteVoteUseCase.deleteVote(deleteVoteCommand)
        return ResponseEntity.ok().body(VoteDtoMapper.dtoToDeleteResponse(deleteVoteResponseDto))
    }

    /**
     * Update vote inbound adapter
     *
     * @param updateVoteRequest request user id with target category and target id
     * @return Boolean about update success
     */
    @PutMapping(GlobalURI.VOTE_ROOT)
    fun updateVote(
        @RequestBody @Valid updateVoteRequest: UpdateVoteRequest
    ): ResponseEntity<UpdateVoteResponse> {
        val updateVoteCommand = VoteDtoMapper.updateRequestToCommand(updateVoteRequest)
        val updateVoteResponseDto = updateVoteUseCase.updateVote(updateVoteCommand)
        return ResponseEntity.ok().body(VoteDtoMapper.dtoToUpdateResponse(updateVoteResponseDto))
    }
}