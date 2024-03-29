package com.fitmate.myfit.adapter.`in`.web.vote.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.vote.request.RegisterVoteRequest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class VoteControllerBootTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private val requestUserId = "testUserId"
    private val agree = true
    private val targetCategory = 1
    private val targetId = 13L

    @Test
    @DisplayName("[통합][Web Adapter] Vote 등록 - 성공 테스트")
    @Throws(Exception::class)
    fun `register vote controller success test`() {
        //given
        val registerVoteRequest =
            RegisterVoteRequest(requestUserId, agree, targetCategory, targetId)

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
}