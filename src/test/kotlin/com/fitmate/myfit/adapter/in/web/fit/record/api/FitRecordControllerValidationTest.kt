package com.fitmate.myfit.adapter.`in`.web.fit.record.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.fit.record.request.DeleteFitRecordRequest
import com.fitmate.myfit.adapter.`in`.web.fit.record.request.RegisterFitRecordRequest
import com.fitmate.myfit.application.port.`in`.fit.record.command.RegisterFitRecordCommand
import com.fitmate.myfit.application.port.`in`.fit.record.response.RegisterFitRecordResponseDto
import com.fitmate.myfit.application.port.`in`.fit.record.usecase.DeleteFitRecordUseCase
import com.fitmate.myfit.application.port.`in`.fit.record.usecase.RegisterFitRecordUseCase
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.Instant

@WebMvcTest(FitRecordController::class)
@AutoConfigureRestDocs
class FitRecordControllerValidationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var registerFitRecordUseCase: RegisterFitRecordUseCase

    @MockBean
    private lateinit var deleteFitRecordUseCase: DeleteFitRecordUseCase

    private val requestUserId = "testUserId"
    private val recordStartDate = Instant.now()
    private val recordEndDate = recordStartDate.plusSeconds(100000)
    private val multiMediaEndPoint: List<String> = listOf("https://avatars.githubusercontent.com/u/105261146?v=4")

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Web Adapter] Fit record 등록 validation - 실패 테스트")
    @Throws(Exception::class)
    fun `register fit record controller user id validation fail test`(testUserId: String) {
        //given
        val registerFitRecordRequest =
            RegisterFitRecordRequest(testUserId, recordStartDate, recordEndDate, multiMediaEndPoint)
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
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Web Adapter] Fit record 삭제 user id validation - 실패 테스트")
    @Throws(Exception::class)
    fun `delete fit record controller user id validation fail test`(testUserId: String) {
        //given
        val deleteFitRecordRequest = DeleteFitRecordRequest(testUserId)

        //when
        val resultActions = mockMvc.perform(
            delete(
                GlobalURI.FIT_RECORD_ROOT + GlobalURI.PATH_VARIABLE_FIT_RECORD_ID_WITH_BRACE, 124L
            ).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteFitRecordRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }
}