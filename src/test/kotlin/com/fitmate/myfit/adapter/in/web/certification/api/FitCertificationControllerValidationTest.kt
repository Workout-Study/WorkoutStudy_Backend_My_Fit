package com.fitmate.myfit.adapter.`in`.web.certification.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.myfit.adapter.`in`.web.certification.request.RegisterFitCertificationRequest
import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.application.port.`in`.certification.usecase.RegisterFitCertificationUseCase
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

@WebMvcTest(FitCertificationController::class)
class FitCertificationControllerValidationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var registerFitCertificationUseCase: RegisterFitCertificationUseCase

    private val requestUserId = "testUserId"
    private val fitRecordId = 137L
    private val fitGroupIds = listOf(13L, 7L, 2L)

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Web Adapter] Fit certification 등록 user id validation - 실패 테스트")
    @Throws(Exception::class)
    fun `register fit certification controller user id validation fail test`(testUserId: String) {
        //given
        val registerFitCertificationRequest =
            RegisterFitCertificationRequest(testUserId, fitRecordId, fitGroupIds)

        //when
        val resultActions = mockMvc.perform(
            post(GlobalURI.FIT_CERTIFICATION_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerFitCertificationRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }
}