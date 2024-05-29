package com.fitmate.myfit.adapter.`in`.web.fit.off.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.fit.off.request.DeleteFitOffRequest
import com.fitmate.myfit.adapter.`in`.web.fit.off.request.RegisterFitOffRequest
import com.fitmate.myfit.adapter.`in`.web.fit.off.request.UpdateFitOffRequest
import com.fitmate.myfit.application.port.`in`.fit.off.command.DeleteFitOffCommand
import com.fitmate.myfit.application.port.`in`.fit.off.command.RegisterFitOffCommand
import com.fitmate.myfit.application.port.`in`.fit.off.command.UpdateFitOffCommand
import com.fitmate.myfit.application.port.`in`.fit.off.response.DeleteFitOffResponseDto
import com.fitmate.myfit.application.port.`in`.fit.off.response.RegisterFitOffResponseDto
import com.fitmate.myfit.application.port.`in`.fit.off.response.UpdateFitOffResponseDto
import com.fitmate.myfit.application.port.`in`.fit.off.usecase.DeleteFitOffUseCase
import com.fitmate.myfit.application.port.`in`.fit.off.usecase.RegisterFitOffUseCase
import com.fitmate.myfit.application.port.`in`.fit.off.usecase.UpdateFitOffUseCase
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
import java.time.LocalDate
import java.time.ZoneId

@WebMvcTest(FitOffController::class)
@AutoConfigureRestDocs
class FitOffControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var registerFitOffUseCase: RegisterFitOffUseCase

    @MockBean
    private lateinit var deleteFitOffUseCase: DeleteFitOffUseCase

    @MockBean
    private lateinit var updateFitOffUseCase: UpdateFitOffUseCase

    private val requestUserId = 642
    private val fitOffStartDate = LocalDate.now().plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
    private val fitOffEndDate = LocalDate.now().plusDays(5).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
    private val fitOffReason = "발목 부상"
    private val fitOffId = 63L

    @Test
    @DisplayName("[단위][Web Adapter] Fit off 등록 - 성공 테스트")
    @Throws(Exception::class)
    fun `register fit off controller success test`() {
        //given
        val registerFitOffRequest =
            RegisterFitOffRequest(requestUserId, fitOffStartDate, fitOffEndDate, fitOffReason)
        val registerFitOffResponseDto = RegisterFitOffResponseDto(true, fitOffId)

        whenever(registerFitOffUseCase.registerFitOff(any<RegisterFitOffCommand>()))
            .thenReturn(registerFitOffResponseDto)
        //when
        val resultActions = mockMvc.perform(
            post(GlobalURI.FIT_OFF_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerFitOffRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isCreated())
            .andDo(print())
            .andDo(
                document(
                    "register-fit-off",
                    requestFields(
                        fieldWithPath("requestUserId").type(JsonFieldType.NUMBER)
                            .description("Fit off를 등록하는 User id"),
                        fieldWithPath("fitOffStartDate").type(JsonFieldType.STRING)
                            .description("Fit off 시작 시간"),
                        fieldWithPath("fitOffEndDate").type(JsonFieldType.STRING)
                            .description("Fit off 종료 시간"),
                        fieldWithPath("fitOffReason").type(JsonFieldType.STRING)
                            .description("Fit off 사유")
                    ),
                    responseFields(
                        fieldWithPath("isRegisterSuccess").type(JsonFieldType.BOOLEAN)
                            .description("등록 성공 여부"),
                        fieldWithPath("fitOffId").type(JsonFieldType.NUMBER)
                            .description("등록 성공 시 fit off id ( 실패시 null )")
                    )
                )
            )
    }

    @Test
    @DisplayName("[단위][Web Adapter] Fit off 삭제 - 성공 테스트")
    @Throws(Exception::class)
    fun `delete fit off controller success test`() {
        //given
        val deleteFitOffRequest = DeleteFitOffRequest(requestUserId)
        val deleteFitOffResponseDto = DeleteFitOffResponseDto(true)

        whenever(deleteFitOffUseCase.deleteFitOff(any<DeleteFitOffCommand>()))
            .thenReturn(deleteFitOffResponseDto)
        //when
        val resultActions = mockMvc.perform(
            delete(
                GlobalURI.FIT_OFF_ROOT + GlobalURI.PATH_VARIABLE_FIT_OFF_ID_WITH_BRACE, fitOffId
            ).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteFitOffRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "delete-fit-off",
                    pathParameters(
                        parameterWithName(GlobalURI.PATH_VARIABLE_FIT_OFF_ID)
                            .description("삭제할 Fit off id")
                    ),
                    requestFields(
                        fieldWithPath("requestUserId").type(JsonFieldType.NUMBER)
                            .description("Fit off를 삭제 요청한 User id")
                    ),
                    responseFields(
                        fieldWithPath("isDeleteSuccess").type(JsonFieldType.BOOLEAN)
                            .description("삭제 성공 여부")
                    )
                )
            )
    }

    @Test
    @DisplayName("[단위][Web Adapter] Fit off 수정 - 성공 테스트")
    @Throws(Exception::class)
    fun `update fit off controller success test`() {
        //given
        val updateFitOffRequest = UpdateFitOffRequest(requestUserId, fitOffStartDate, fitOffEndDate, fitOffReason)
        val updateFitOffResponseDto = UpdateFitOffResponseDto(true)

        whenever(updateFitOffUseCase.updateFitOff(any<UpdateFitOffCommand>()))
            .thenReturn(updateFitOffResponseDto)
        //when
        val resultActions = mockMvc.perform(
            put(
                GlobalURI.FIT_OFF_ROOT + GlobalURI.PATH_VARIABLE_FIT_OFF_ID_WITH_BRACE, fitOffId
            ).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateFitOffRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "update-fit-off",
                    pathParameters(
                        parameterWithName(GlobalURI.PATH_VARIABLE_FIT_OFF_ID)
                            .description("수정할 Fit off id")
                    ),
                    requestFields(
                        fieldWithPath("requestUserId").type(JsonFieldType.NUMBER)
                            .description("Fit off를 수정 요청한 User id"),
                        fieldWithPath("fitOffStartDate").type(JsonFieldType.STRING)
                            .description("Fit off 시작 시간 ( 기존 등록했던 시간과 동일하거나 Update를 요청하는 시간보다 이후여야함 )"),
                        fieldWithPath("fitOffEndDate").type(JsonFieldType.STRING)
                            .description("Fit off 종료 시간 ( 이미 지나지 않았고 Update를 요청하는 시간보다 이후여야함 )"),
                        fieldWithPath("fitOffReason").type(JsonFieldType.STRING)
                            .description("Fit off 사유")
                    ),
                    responseFields(
                        fieldWithPath("isUpdateSuccess").type(JsonFieldType.BOOLEAN)
                            .description("수정 성공 여부")
                    )
                )
            )
    }
}