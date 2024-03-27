package com.fitmate.myfit.adapter.`in`.web.certification.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.myfit.adapter.`in`.web.certification.request.RegisterFitCertificationRequest
import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.application.port.`in`.certification.command.RegisterFitCertificationCommand
import com.fitmate.myfit.application.port.`in`.certification.response.RegisterFitCertificationResponseDto
import com.fitmate.myfit.application.port.`in`.certification.usecase.RegisterFitCertificationUseCase
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

@WebMvcTest(FitCertificationController::class)
@AutoConfigureRestDocs
class FitCertificationControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var registerFitCertificationUseCase: RegisterFitCertificationUseCase

    private val requestUserId = "testUserId"
    private val fitRecordId = 137L
    private val fitGroupIds = listOf(13L, 7L, 2L)

    @Test
    @DisplayName("[단위][Web Adapter] Fit certification 등록 - 성공 테스트")
    @Throws(Exception::class)
    fun `register fit certification controller success test`() {
        //given
        val registerFitCertificationRequest =
            RegisterFitCertificationRequest(requestUserId, fitRecordId, fitGroupIds)
        val registerFitCertificationResponseDto = RegisterFitCertificationResponseDto(true)

        whenever(registerFitCertificationUseCase.registerFitCertification(any<RegisterFitCertificationCommand>()))
            .thenReturn(registerFitCertificationResponseDto)
        //when
        val resultActions = mockMvc.perform(
            post(GlobalURI.FIT_CERTIFICATION_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerFitCertificationRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isCreated())
            .andDo(print())
            .andDo(
                document(
                    "register-fit-certification",
                    requestFields(
                        fieldWithPath("requestUserId").type(JsonFieldType.STRING)
                            .description("Fit 인증을 등록하는 User id"),
                        fieldWithPath("fitRecordId").type(JsonFieldType.NUMBER)
                            .description("인증으로 등록할 record의 id"),
                        fieldWithPath("fitGroupIds").type(JsonFieldType.ARRAY)
                            .description("인증으로 등록할 group의 id list")
                    ),
                    responseFields(
                        fieldWithPath("isRegisterSuccess").type(JsonFieldType.BOOLEAN)
                            .description("등록 성공 여부")
                    )
                )
            )
    }
}