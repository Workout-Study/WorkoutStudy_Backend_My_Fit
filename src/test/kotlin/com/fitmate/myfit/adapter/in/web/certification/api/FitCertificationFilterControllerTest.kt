package com.fitmate.myfit.adapter.`in`.web.certification.api

import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.application.port.`in`.certification.usecase.ReadFitCertificationUseCase
import com.fitmate.myfit.application.port.`in`.my.fit.command.FitCertificationFilterByGroupCommand
import com.fitmate.myfit.application.port.`in`.my.fit.response.FitCertificationDetailResponseDto
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

@WebMvcTest(FitCertificationFilterController::class)
@AutoConfigureRestDocs
class FitCertificationFilterControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var readFitCertificationUseCase: ReadFitCertificationUseCase

    private val userId = "testUserId"
    private val fitGroupId = 12L

    @Test
    @DisplayName("[단위][Web Adapter] Fit certification by fit group list 조회 - 성공 테스트")
    @Throws(Exception::class)
    fun `get fit certification list by fit group controller success test`() {
        //given
        val fitCertificationDetails = mutableListOf<FitCertificationDetailResponseDto>()

        for (i in 0..3) {
            fitCertificationDetails.add(
                FitCertificationDetailResponseDto(
                    i.toLong(),
                    i.toLong(),
                    userId + i,
                    true,
                    false,
                    i + 4,
                    i + 1,
                    i + 13,
                    Instant.now(),
                    Instant.now().plusSeconds(10000),
                    Instant.now().plusSeconds(1000000)
                )
            )
        }

        whenever(readFitCertificationUseCase.getFitCertificationByGroupId(any<FitCertificationFilterByGroupCommand>()))
            .thenReturn(fitCertificationDetails)
        //when
        val resultActions = mockMvc.perform(
            get(
                GlobalURI.FIT_CERTIFICATION_FILTER
                        + GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE
                        + GlobalURI.PATH_VARIABLE_USER_ID_WITH_BRACE,
                fitGroupId,
                userId
            ).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "fit-certification-list-by-fit-group",
                    pathParameters(
                        parameterWithName(GlobalURI.PATH_VARIABLE_FIT_GROUP_ID)
                            .description("진행중인 Fit certification list를 조회할 group id"),
                        parameterWithName(GlobalURI.PATH_VARIABLE_USER_ID)
                            .description("Fit certification list를 조회하는 User id")
                    ),
                    responseFields(
                        fieldWithPath("fitCertificationDetails[]").type(JsonFieldType.ARRAY)
                            .description("진행중인 인증 list"),
                        fieldWithPath("fitCertificationDetails[].certificationId").type(JsonFieldType.NUMBER)
                            .description("운동 인증 id (fit certification id)"),
                        fieldWithPath("fitCertificationDetails[].recordId").type(JsonFieldType.NUMBER)
                            .description("운동 인증의 record id"),
                        fieldWithPath("fitCertificationDetails[].certificationRequestUserId").type(JsonFieldType.STRING)
                            .description("운동 인증을 요청한 user id"),
                        fieldWithPath("fitCertificationDetails[].isUserVoteDone").type(JsonFieldType.BOOLEAN)
                            .description("조회를 요청한 유저가 투표 했는지 여부"),
                        fieldWithPath("fitCertificationDetails[].isUserAgree").type(JsonFieldType.BOOLEAN)
                            .description("조회를 요청한 유저가 찬성 했는지 여부"),
                        fieldWithPath("fitCertificationDetails[].agreeCount").type(JsonFieldType.NUMBER)
                            .description("찬성 수"),
                        fieldWithPath("fitCertificationDetails[].disagreeCount").type(JsonFieldType.NUMBER)
                            .description("반대 수"),
                        fieldWithPath("fitCertificationDetails[].maxAgreeCount").type(JsonFieldType.NUMBER)
                            .description("최대 투표 수"),
                        fieldWithPath("fitCertificationDetails[].fitRecordStartDate").type(JsonFieldType.STRING)
                            .description("기록 시작 일자"),
                        fieldWithPath("fitCertificationDetails[].fitRecordEndDate").type(JsonFieldType.STRING)
                            .description("기록 종료 일자"),
                        fieldWithPath("fitCertificationDetails[].voteEndDate").type(JsonFieldType.STRING)
                            .description("투표 종료 일자")
                    )
                )
            )
    }
}