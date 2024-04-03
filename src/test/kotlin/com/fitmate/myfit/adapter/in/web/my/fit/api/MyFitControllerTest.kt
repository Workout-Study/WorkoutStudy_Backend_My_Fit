package com.fitmate.myfit.adapter.`in`.web.my.fit.api

import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.my.fit.request.FitCertificationProgressFilterRequest
import com.fitmate.myfit.application.port.`in`.my.fit.command.FitCertificationProgressFilterCommand
import com.fitmate.myfit.application.port.`in`.my.fit.response.FilterCertificationProgressResponseDto
import com.fitmate.myfit.application.port.`in`.my.fit.usecase.ReadFitCertificationProgressUseCase
import org.junit.jupiter.api.Assertions.*
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
import org.springframework.web.util.UriComponentsBuilder

@WebMvcTest(MyFitController::class)
@AutoConfigureRestDocs
class MyFitControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var readFitCertificationProgressUseCase: ReadFitCertificationProgressUseCase

    private val requestUserId = "testUserId"
    private val fitGroupName = "헬창들은 일주일에 7번 운동해야죠 스터디"
    private val cycle = 1
    private val frequency = 7
    private val certificationCount = frequency - 3

    @Test
    @DisplayName("[단위][Web Adapter] Fit certification progress list 조회 - 성공 테스트")
    @Throws(Exception::class)
    fun `get fit certification progress list controller success test`() {
        //given
        val request = FitCertificationProgressFilterRequest(requestUserId)

        val fitCertificationProgresses = mutableListOf<FilterCertificationProgressResponseDto>()

        for (i in 1..3) {
            fitCertificationProgresses.add(
                FilterCertificationProgressResponseDto(
                    i.toLong(),
                    fitGroupName + i,
                    cycle,
                    frequency + i,
                    certificationCount + i
                )
            )
        }

        val queryString = UriComponentsBuilder.newInstance()
            .queryParam("requestUserId", request.requestUserId)
            .build()
            .encode()
            .toUriString()

        whenever(readFitCertificationProgressUseCase.filterFitCertificationProgress(any<FitCertificationProgressFilterCommand>()))
            .thenReturn(fitCertificationProgresses)
        //when
        val resultActions = mockMvc.perform(
            get(GlobalURI.MY_FIT_CERTIFICATION_PROGRESS + queryString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "fit-certification-progress",
                    queryParameters(
                        parameterWithName("requestUserId")
                            .description("Fit certification progress(인증 진척도)를 조회하는 User id")
                    ),
                    responseFields(
                        fieldWithPath("fitCertificationProgresses[]").type(JsonFieldType.ARRAY)
                            .description("등록 성공 여부"),
                        fieldWithPath("fitCertificationProgresses[].fitGroupId").type(JsonFieldType.NUMBER)
                            .description("fit group id"),
                        fieldWithPath("fitCertificationProgresses[].fitGroupName").type(JsonFieldType.STRING)
                            .description("fit group 이름"),
                        fieldWithPath("fitCertificationProgresses[].cycle").type(JsonFieldType.NUMBER)
                            .description("인증 주기 ( 1: 일주일, 2: 한달, 3: 일년 ) - 현재는 일주일만 구현 "),
                        fieldWithPath("fitCertificationProgresses[].frequency").type(JsonFieldType.NUMBER)
                            .description("주기별 인증 필요 횟수"),
                        fieldWithPath("fitCertificationProgresses[].certificationCount").type(JsonFieldType.NUMBER)
                            .description("금주 인증 횟수"),
                    )
                )
            )
    }
}