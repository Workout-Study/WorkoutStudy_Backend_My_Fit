package com.fitmate.myfit.adapter.`in`.web.vote.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.vote.request.DeleteVoteRequest
import com.fitmate.myfit.adapter.`in`.web.vote.request.RegisterVoteRequest
import com.fitmate.myfit.adapter.`in`.web.vote.request.UpdateVoteRequest
import com.fitmate.myfit.application.port.`in`.vote.usecase.DeleteVoteUseCase
import com.fitmate.myfit.application.port.`in`.vote.usecase.RegisterVoteUseCase
import com.fitmate.myfit.application.port.`in`.vote.usecase.UpdateVoteUseCase
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(VoteController::class)
class VoteControllerValidationTest {

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

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Web Adapter] Vote 등록 user id validation - 실패 테스트")
    @Throws(Exception::class)
    fun `register vote controller user id validation fail test`(testUserId: String) {
        //given
        val registerVoteRequest =
            RegisterVoteRequest(testUserId, agree, targetCategory, targetId)

        //when
        val resultActions = mockMvc.perform(
            post(GlobalURI.VOTE_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerVoteRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Web Adapter] Vote 삭제 user id validation - 실패 테스트")
    @Throws(Exception::class)
    fun `delete vote controller user id validation fail test`(testUserId: String) {
        //given
        val deleteVoteRequest =
            DeleteVoteRequest(testUserId, targetCategory, targetId)

        //when
        val resultActions = mockMvc.perform(
            delete(GlobalURI.VOTE_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteVoteRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Web Adapter] Vote 수정 user id validation - 실패 테스트")
    @Throws(Exception::class)
    fun `update vote controller user id validation fail test`(testUserId: String) {
        //given
        val updateVoteRequest =
            UpdateVoteRequest(testUserId, agree, targetCategory, targetId)

        //when
        val resultActions = mockMvc.perform(
            put(GlobalURI.VOTE_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateVoteRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }
}