package com.fitmate.myfit.adapter.`in`.web.fit.record.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.fit.record.request.DeleteFitRecordRequest
import com.fitmate.myfit.adapter.`in`.web.fit.record.request.RegisterFitRecordRequest
import com.fitmate.myfit.adapter.`in`.web.fit.record.request.UpdateFitRecordMultiMediaEndPointRequest
import com.fitmate.myfit.adapter.out.api.SenderUtils
import com.fitmate.myfit.application.port.`in`.fit.record.command.DeleteFitRecordCommand
import com.fitmate.myfit.application.port.`in`.fit.record.command.RegisterFitRecordCommand
import com.fitmate.myfit.application.port.`in`.fit.record.command.UpdateFitRecordMultiMediaEndPointCommand
import com.fitmate.myfit.application.port.`in`.fit.record.response.DeleteFitRecordResponseDto
import com.fitmate.myfit.application.port.`in`.fit.record.response.RegisterFitRecordResponseDto
import com.fitmate.myfit.application.port.`in`.fit.record.response.UpdateFitRecordMultiMediaEndPointResponseDto
import com.fitmate.myfit.application.port.`in`.fit.record.usecase.DeleteFitRecordUseCase
import com.fitmate.myfit.application.port.`in`.fit.record.usecase.RegisterFitRecordUseCase
import com.fitmate.myfit.application.port.`in`.fit.record.usecase.UpdateFitRecordMultiMediaEndPointUseCase
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
import org.springframework.restdocs.request.RequestDocumentation.*
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

    @MockBean
    private lateinit var deleteFitRecordUseCase: DeleteFitRecordUseCase

    @MockBean
    private lateinit var updateFitRecordMultiMediaEndPointUseCase: UpdateFitRecordMultiMediaEndPointUseCase

    @MockBean
    private lateinit var senderUtils: SenderUtils

    private val requestUserId = 642
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
        val registerFitRecordResponseDto = RegisterFitRecordResponseDto(true, 1L)

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
                        fieldWithPath("requestUserId").type(JsonFieldType.NUMBER)
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
                            .description("등록 성공 여부"),
                        fieldWithPath("fitRecordId").type(JsonFieldType.NUMBER)
                            .description("등록 시 fit record id ( 실패시 null )")
                    )
                )
            )
    }

    @Test
    @DisplayName("[단위][Web Adapter] Fit record 삭제 - 성공 테스트")
    @Throws(Exception::class)
    fun `delete fit record controller success test`() {
        //given
        val deleteFitRecordRequest = DeleteFitRecordRequest(requestUserId)
        val deleteFitRecordResponseDto = DeleteFitRecordResponseDto(true)

        whenever(deleteFitRecordUseCase.deleteFitRecord(any<DeleteFitRecordCommand>()))
            .thenReturn(deleteFitRecordResponseDto)
        //when
        val resultActions = mockMvc.perform(
            delete(
                GlobalURI.FIT_RECORD_ROOT + GlobalURI.PATH_VARIABLE_FIT_RECORD_ID_WITH_BRACE, 124L
            ).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteFitRecordRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "delete-fit-record",
                    pathParameters(
                        parameterWithName(GlobalURI.PATH_VARIABLE_FIT_RECORD_ID)
                            .description("삭제할 Fit record id")
                    ),
                    requestFields(
                        fieldWithPath("requestUserId").type(JsonFieldType.NUMBER)
                            .description("Fit record를 삭제 요청한 User id")
                    ),
                    responseFields(
                        fieldWithPath("isDeleteSuccess").type(JsonFieldType.BOOLEAN)
                            .description("삭제 성공 여부")
                    )
                )
            )
    }

    @Test
    @DisplayName("[단위][Web Adapter] Fit record 사진 수정 - 성공 테스트")
    @Throws(Exception::class)
    fun `update fit record multi media end point controller success test`() {
        //given
        val request =
            UpdateFitRecordMultiMediaEndPointRequest(requestUserId, multiMediaEndPoint)
        val responseDto = UpdateFitRecordMultiMediaEndPointResponseDto(true)

        whenever(updateFitRecordMultiMediaEndPointUseCase.updateFitRecordMultiMediaEndPoint(any<UpdateFitRecordMultiMediaEndPointCommand>()))
            .thenReturn(responseDto)
        //when
        val resultActions = mockMvc.perform(
            patch(GlobalURI.FIT_RECORD_ROOT + GlobalURI.PATH_VARIABLE_FIT_RECORD_ID_WITH_BRACE, 124L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "update-fit-record-multimedia-end-point",
                    pathParameters(
                        parameterWithName(GlobalURI.PATH_VARIABLE_FIT_RECORD_ID)
                            .description("update할 Fit record id")
                    ),
                    requestFields(
                        fieldWithPath("requestUserId").type(JsonFieldType.NUMBER)
                            .description("Fit record를 등록하는 User id"),
                        fieldWithPath("multiMediaEndPoints").type(JsonFieldType.ARRAY)
                            .description("멀티 미디어 end point list ( 주어진 index 순으로 return ) - 기존 등록 돼있었던 url들 전부 삭제함")
                    ),
                    responseFields(
                        fieldWithPath("isUpdateSuccess").type(JsonFieldType.BOOLEAN)
                            .description("등록 성공 여부")
                    )
                )
            )
    }
}