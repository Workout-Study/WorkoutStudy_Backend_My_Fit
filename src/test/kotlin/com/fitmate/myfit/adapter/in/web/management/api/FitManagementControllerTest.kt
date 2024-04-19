package com.fitmate.myfit.adapter.`in`.web.management.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.management.request.NoNeedPayFitPenaltyRequest
import com.fitmate.myfit.adapter.`in`.web.management.request.PaidFitPenaltyRequest
import com.fitmate.myfit.application.port.`in`.management.command.NoNeedPayFitPenaltyCommand
import com.fitmate.myfit.application.port.`in`.management.command.PaidFitPenaltyCommand
import com.fitmate.myfit.application.port.`in`.management.response.NoNeedPayFitPenaltyResponseDto
import com.fitmate.myfit.application.port.`in`.management.response.PaidFitPenaltyResponseDto
import com.fitmate.myfit.application.port.`in`.management.usecase.UpdateFitPenaltyUseCase
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

@WebMvcTest(FitManagementController::class)
@AutoConfigureRestDocs
class FitManagementControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var updateFitPenaltyUseCase: UpdateFitPenaltyUseCase

    private val fitPenaltyId = 162L
    private val requestUserId = "testUserId"

    @Test
    @DisplayName("[단위][Web Adapter] Fit management penalty 입금 완료 by fit leader - 성공 테스트")
    @Throws(Exception::class)
    fun `fit penalty paid by fit leader controller success test`() {
        //given
        val paidFitPenaltyRequest = PaidFitPenaltyRequest(requestUserId)
        val paidFitPenaltyResponseDto = PaidFitPenaltyResponseDto(true)

        whenever(updateFitPenaltyUseCase.paidFitPenaltyByFitReader(any<PaidFitPenaltyCommand>()))
            .thenReturn(paidFitPenaltyResponseDto)
        //when
        val resultActions = mockMvc.perform(
            put(GlobalURI.FIT_PENALTY_MANAGEMENT + GlobalURI.PATH_VARIABLE_FIT_PENALTY_ID_WITH_BRACE, fitPenaltyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paidFitPenaltyRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "fit-management-penalty-paid-by-fit-leader",
                    pathParameters(
                        parameterWithName(GlobalURI.PATH_VARIABLE_FIT_PENALTY_ID)
                            .description("입금 완료 처리할 fit penalty id"),
                    ),
                    requestFields(
                        fieldWithPath("requestUserId").type(JsonFieldType.STRING)
                            .description("Fit penalty가 수납완료 됐음을 처리할 User id ( fit leader 여야함 )")
                    ),
                    responseFields(
                        fieldWithPath("isPaidSuccess").type(JsonFieldType.BOOLEAN)
                            .description("수납 완료 등록 성공 여부")
                    )
                )
            )
    }

    @Test
    @DisplayName("[단위][Web Adapter] Fit management penalty 입금 불필요 처리 by fit leader - 성공 테스트")
    @Throws(Exception::class)
    fun `fit penalty no need pay by fit leader controller success test`() {
        //given
        val noNeedPayFitPenaltyRequest = NoNeedPayFitPenaltyRequest(requestUserId)
        val noNeedPayFitPenaltyResponseDto = NoNeedPayFitPenaltyResponseDto(true)

        whenever(updateFitPenaltyUseCase.noNeedPayFitPenaltyByFitReader(any<NoNeedPayFitPenaltyCommand>()))
            .thenReturn(noNeedPayFitPenaltyResponseDto)
        //when
        val resultActions = mockMvc.perform(
            delete(GlobalURI.FIT_PENALTY_MANAGEMENT + GlobalURI.PATH_VARIABLE_FIT_PENALTY_ID_WITH_BRACE, fitPenaltyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(noNeedPayFitPenaltyRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "fit-management-penalty-no-need-pay-by-fit-leader",
                    pathParameters(
                        parameterWithName(GlobalURI.PATH_VARIABLE_FIT_PENALTY_ID)
                            .description("입금 불필요 처리할 fit penalty id"),
                    ),
                    requestFields(
                        fieldWithPath("requestUserId").type(JsonFieldType.STRING)
                            .description("Fit penalty가 입금불필요 됐음을 처리할 User id ( fit leader 여야함 )")
                    ),
                    responseFields(
                        fieldWithPath("isNoNeedPaySuccess").type(JsonFieldType.BOOLEAN)
                            .description("입금 불필요 등록 성공 여부")
                    )
                )
            )
    }
}