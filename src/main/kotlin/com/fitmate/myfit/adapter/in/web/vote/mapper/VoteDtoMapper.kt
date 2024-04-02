package com.fitmate.myfit.adapter.`in`.web.vote.mapper

import com.fitmate.myfit.adapter.`in`.web.vote.request.DeleteVoteRequest
import com.fitmate.myfit.adapter.`in`.web.vote.request.RegisterVoteRequest
import com.fitmate.myfit.adapter.`in`.web.vote.request.UpdateVoteRequest
import com.fitmate.myfit.adapter.`in`.web.vote.response.DeleteVoteResponse
import com.fitmate.myfit.adapter.`in`.web.vote.response.RegisterVoteResponse
import com.fitmate.myfit.adapter.`in`.web.vote.response.UpdateVoteResponse
import com.fitmate.myfit.application.port.`in`.vote.command.DeleteVoteCommand
import com.fitmate.myfit.application.port.`in`.vote.command.RegisterVoteCommand
import com.fitmate.myfit.application.port.`in`.vote.command.UpdateVoteCommand
import com.fitmate.myfit.application.port.`in`.vote.response.DeleteVoteResponseDto
import com.fitmate.myfit.application.port.`in`.vote.response.RegisterVoteResponseDto
import com.fitmate.myfit.application.port.`in`.vote.response.UpdateVoteResponseDto

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

        fun deleteRequestToCommand(deleteVoteRequest: DeleteVoteRequest): DeleteVoteCommand =
            DeleteVoteCommand(
                deleteVoteRequest.requestUserId,
                deleteVoteRequest.targetCategory,
                deleteVoteRequest.targetId
            )

        fun dtoToDeleteResponse(deleteVoteResponseDto: DeleteVoteResponseDto): DeleteVoteResponse =
            DeleteVoteResponse(deleteVoteResponseDto.isDeleteSuccess)

        fun updateRequestToCommand(updateVoteRequest: UpdateVoteRequest): UpdateVoteCommand =
            UpdateVoteCommand(
                updateVoteRequest.requestUserId,
                updateVoteRequest.agree,
                updateVoteRequest.targetCategory,
                updateVoteRequest.targetId
            )

        fun dtoToUpdateResponse(updateVoteResponseDto: UpdateVoteResponseDto): UpdateVoteResponse =
            UpdateVoteResponse(updateVoteResponseDto.isUpdateSuccess)
    }
}