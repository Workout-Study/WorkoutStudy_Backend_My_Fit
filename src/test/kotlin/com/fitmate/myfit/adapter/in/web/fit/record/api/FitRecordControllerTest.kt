package com.fitmate.myfit.adapter.`in`.web.fit.record.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.fit.record.request.RegisterFitRecordRequest
import com.fitmate.myfit.application.port.`in`.command.RegisterFitRecordCommand
import com.fitmate.myfit.application.port.`in`.response.RegisterFitRecordResponseDto
import com.fitmate.myfit.application.port.`in`.usecase.RegisterFitRecordUseCase
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
import java.time.Instant

@WebMvcTest(FitRecordController::class)
@AutoConfigureRestDocs
class FitRecordControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var registerFitRecordUseCase: RegisterFitRecordUseCase

    private val requestUserId = "testUserId"
    private val recordStartDate = Instant.now()
    private val recordEndDate = recordStartDate.plusSeconds(100000)
    private val multiMediaEndPoint: List<String> = listOf("https://avatars.githubusercontent.com/u/105261146?v=4")

    @Test
    @DisplayName("[단위][Web Adapter] Fit record 등록 - 성공 테스트")
    @Throws(Exception::class)
    fun `register fit record controller success test`() {
        //given
        val registerFitRecordRequest =
            RegisterFitRecordRequest(requestUserId, recordStartDate, recordEndDate, multiMediaEndPoint)
        val registerFitRecordResponseDto = RegisterFitRecordResponseDto(true)

        whenever(registerFitRecordUseCase.registerFitRecord(any<RegisterFitRecordCommand>()))
            .thenReturn(registerFitRecordResponseDto)
        //when
        val resultActions = mockMvc.perform(
            post(GlobalURI.FIT_RECORD_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerFitRecordRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isCreated())
            .andDo(print())
            .andDo(
                document(
                    "register-fit-record",
                    requestFields(
                        fieldWithPath("requestUserId").type(JsonFieldType.STRING)
                            .description("Fit record를 등록하는 User id"),
                        fieldWithPath("recordStartDate").type(JsonFieldType.STRING)
                            .description("기록 시작 시간"),
                        fieldWithPath("recordEndDate").type(JsonFieldType.STRING)
                            .description("기록 종료 시간"),
                        fieldWithPath("multiMediaEndPoints").type(JsonFieldType.ARRAY)
                            .description("멀티 미디어 end point list ( 주어진 index 순으로 return )")
                    ),
                    responseFields(
                        fieldWithPath("isRegisterSuccess").type(JsonFieldType.BOOLEAN)
                            .description("등록 성공 여부")
                    )
                )
            )
    }
}