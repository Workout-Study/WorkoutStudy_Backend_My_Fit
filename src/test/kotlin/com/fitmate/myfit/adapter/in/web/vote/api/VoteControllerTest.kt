package com.fitmate.myfit.adapter.`in`.web.vote.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.vote.request.DeleteVoteRequest
import com.fitmate.myfit.adapter.`in`.web.vote.request.RegisterVoteRequest
import com.fitmate.myfit.adapter.`in`.web.vote.request.UpdateVoteRequest
import com.fitmate.myfit.application.port.`in`.vote.command.DeleteVoteCommand
import com.fitmate.myfit.application.port.`in`.vote.command.RegisterVoteCommand
import com.fitmate.myfit.application.port.`in`.vote.command.UpdateVoteCommand
import com.fitmate.myfit.application.port.`in`.vote.response.DeleteVoteResponseDto
import com.fitmate.myfit.application.port.`in`.vote.response.RegisterVoteResponseDto
import com.fitmate.myfit.application.port.`in`.vote.response.UpdateVoteResponseDto
import com.fitmate.myfit.application.port.`in`.vote.usecase.DeleteVoteUseCase
import com.fitmate.myfit.application.port.`in`.vote.usecase.RegisterVoteUseCase
import com.fitmate.myfit.application.port.`in`.vote.usecase.UpdateVoteUseCase
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(VoteController::class)
@AutoConfigureRestDocs
class VoteControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var registerVoteUseCase: RegisterVoteUseCase

    @MockBean
    private lateinit var deleteVoteUseCase: DeleteVoteUseCase

    @MockBean
    private lateinit var updateVoteUseCase: UpdateVoteUseCase

    private val requestUserId = "testUserId"
    private val agree = true
    private val targetCategory = 1
    private val targetId = 13L

    @Test
    @DisplayName("[단위][Web Adapter] Vote 등록 - 성공 테스트")
    @Throws(Exception::class)
    fun `register vote controller success test`() {
        //given
        val registerVoteRequest =
            RegisterVoteRequest(requestUserId, agree, targetCategory, targetId)
        val registerVoteResponseDto = RegisterVoteResponseDto(true)

        whenever(registerVoteUseCase.registerVote(any<RegisterVoteCommand>()))
            .thenReturn(registerVoteResponseDto)
        //when
        val resultActions = mockMvc.perform(
            post(GlobalURI.VOTE_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerVoteRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isCreated())
            .andDo(print())
            .andDo(
                document(
                    "register-vote",
                    requestFields(
                        fieldWithPath("requestUserId").type(JsonFieldType.STRING)
                            .description("Vote를 등록하는 User id"),
                        fieldWithPath("agree").type(JsonFieldType.BOOLEAN)
                            .description("찬반 여부"),
                        fieldWithPath("targetCategory").type(JsonFieldType.NUMBER)
                            .description("투표를 등록할 target의 category ( target category - 1: fit certification )"),
                        fieldWithPath("targetId").type(JsonFieldType.NUMBER)
                            .description("투표를 등록할 target의 id ( ex. fit certification id )")
                    ),
                    responseFields(
                        fieldWithPath("isRegisterSuccess").type(JsonFieldType.BOOLEAN)
                            .description("등록 성공 여부")
                    )
                )
            )
    }

    @Test
    @DisplayName("[단위][Web Adapter] Vote 삭제 - 성공 테스트")
    @Throws(Exception::class)
    fun `delete vote controller success test`() {
        //given
        val deleteVoteRequest = DeleteVoteRequest(requestUserId, targetCategory, targetId)
        val deleteVoteResponseDto = DeleteVoteResponseDto(true)

        whenever(deleteVoteUseCase.deleteVote(any<DeleteVoteCommand>()))
            .thenReturn(deleteVoteResponseDto)
        //when
        val resultActions = mockMvc.perform(
            delete(GlobalURI.VOTE_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteVoteRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk)
            .andDo(print())
            .andDo(
                document(
                    "delete-vote",
                    requestFields(
                        fieldWithPath("requestUserId").type(JsonFieldType.STRING)
                            .description("Vote를 삭제요청한 User id"),
                        fieldWithPath("targetCategory").type(JsonFieldType.NUMBER)
                            .description("투표를 삭제할 target의 category ( target category - 1: fit certification )"),
                        fieldWithPath("targetId").type(JsonFieldType.NUMBER)
                            .description("투표를 삭제할 target의 id ( ex. fit certification id )")
                    ),
                    responseFields(
                        fieldWithPath("isDeleteSuccess").type(JsonFieldType.BOOLEAN)
                            .description("삭제 성공 여부")
                    )
                )
            )
    }

    @Test
    @DisplayName("[단위][Web Adapter] Vote 수정 - 성공 테스트")
    @Throws(Exception::class)
    fun `update vote controller success test`() {
        //given
        val updateVoteRequest =
            UpdateVoteRequest(requestUserId, agree, targetCategory, targetId)
        val updateVoteResponseDto = UpdateVoteResponseDto(true)

        whenever(updateVoteUseCase.updateVote(any<UpdateVoteCommand>()))
            .thenReturn(updateVoteResponseDto)
        //when
        val resultActions = mockMvc.perform(
            put(GlobalURI.VOTE_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateVoteRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "update-vote",
                    requestFields(
                        fieldWithPath("requestUserId").type(JsonFieldType.STRING)
                            .description("Vote를 수정요청한 User id"),
                        fieldWithPath("agree").type(JsonFieldType.BOOLEAN)
                            .description("찬반 여부"),
                        fieldWithPath("targetCategory").type(JsonFieldType.NUMBER)
                            .description("투표를 수정할 target의 category ( target category - 1: fit certification )"),
                        fieldWithPath("targetId").type(JsonFieldType.NUMBER)
                            .description("투표를 수정할 target의 id ( ex. fit certification id )")
                    ),
                    responseFields(
                        fieldWithPath("isUpdateSuccess").type(JsonFieldType.BOOLEAN)
                            .description("등록 성공 여부")
                    )
                )
            )
    }
}