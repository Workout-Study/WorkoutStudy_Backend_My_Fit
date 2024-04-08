package com.fitmate.myfit.adapter.`in`.web.vote.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.vote.request.DeleteVoteRequest
import com.fitmate.myfit.adapter.`in`.web.vote.request.RegisterVoteRequest
import com.fitmate.myfit.adapter.`in`.web.vote.request.UpdateVoteRequest
import com.fitmate.myfit.adapter.out.persistence.entity.VoteEntity
import com.fitmate.myfit.adapter.out.persistence.repository.VoteRepository
import com.fitmate.myfit.application.port.`in`.vote.command.RegisterVoteCommand
import com.fitmate.myfit.domain.Vote
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = ["classpath:application-test.yml"])
class VoteControllerBootTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var voteRepository: VoteRepository

    private val requestUserId = "testUserId"
    private val agree = true
    private val targetCategory = 1
    private val targetId = 13L

    @BeforeEach
    fun setUp() {
        voteRepository.save(
            VoteEntity.domainToEntity(
                Vote.createByCommand(
                    RegisterVoteCommand(
                        requestUserId,
                        agree,
                        targetCategory,
                        targetId
                    )
                )
            )
        )
    }

    @Test
    @DisplayName("[통합][Web Adapter] Vote 등록 - 성공 테스트")
    @Throws(Exception::class)
    fun `register vote controller success test`() {
        //given
        val registerVoteRequest =
            RegisterVoteRequest(requestUserId, agree, targetCategory, targetId)

        voteRepository.deleteAll()

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
    }

    @Test
    @DisplayName("[통합][Web Adapter] Vote 삭제 - 성공 테스트")
    @Throws(Exception::class)
    fun `delete vote controller success test`() {
        //given
        val deleteVoteRequest = DeleteVoteRequest(requestUserId, targetCategory, targetId)

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
    }

    @Test
    @DisplayName("[통합][Web Adapter] Vote 수정 - 성공 테스트")
    @Throws(Exception::class)
    fun `update vote controller success test`() {
        //given


        val updateVoteRequest =
            UpdateVoteRequest(requestUserId, agree, targetCategory, targetId)

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
    }
}