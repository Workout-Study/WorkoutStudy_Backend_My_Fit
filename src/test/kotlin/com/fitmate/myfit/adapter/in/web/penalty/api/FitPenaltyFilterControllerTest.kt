package com.fitmate.myfit.adapter.`in`.web.penalty.api

import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.penalty.request.FitPenaltyFilterByUserRequest
import com.fitmate.myfit.adapter.`in`.web.penalty.response.FitPenaltyFilteredResponse
import com.fitmate.myfit.application.port.`in`.fit.penalty.command.FitPenaltyFilterByUserCommand
import com.fitmate.myfit.application.port.`in`.fit.penalty.response.FitPenaltyFilteredResponseDto
import com.fitmate.myfit.application.port.`in`.fit.penalty.usecase.ReadFitPenaltyUseCase
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
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

@WebMvcTest(FitPenaltyFilterController::class)
@AutoConfigureRestDocs
class FitPenaltyFilterControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var readFitPenaltyUseCase: ReadFitPenaltyUseCase

    private val startDate: Instant =
        LocalDate.now().withDayOfMonth(1)
            .atStartOfDay().toInstant(ZoneOffset.UTC)
    private val endDate: Instant =
        LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())
            .atStartOfDay().plusHours(23).plusMinutes(59).plusSeconds(59).toInstant(ZoneOffset.UTC)
    private val requestUserId = "testUserId"

    private val pageNumber = 0
    private val pageSize = 5

    private val totalAmount = 15000

    @Test
    @DisplayName("[단위][Web Adapter] Fit penalty filter by user - 성공 테스트")
    @Throws(Exception::class)
    fun `filter fit penalty by user id controller success test`() {
        //given
        val request = FitPenaltyFilterByUserRequest(
            null,
            startDate,
            endDate,
            true,
            false,
            pageNumber,
            pageSize
        )

        val fitPenaltyFilteredResponseDtoList = mutableListOf<FitPenaltyFilteredResponseDto>()

        for (i in 1..3) {
            fitPenaltyFilteredResponseDtoList.add(
                FitPenaltyFilteredResponseDto(
                    i.toLong(),
                    i.toLong() + 3,
                    requestUserId + i,
                    5000,
                    true,
                    false,
                    Instant.now()
                )
            )
        }

        val fitPenaltyFilteredResponse = FitPenaltyFilteredResponse(
            pageNumber,
            pageSize,
            false,
            totalAmount,
            fitPenaltyFilteredResponseDtoList
        )

        whenever(readFitPenaltyUseCase.fitPenaltyFilterByUser(any<FitPenaltyFilterByUserCommand>()))
            .thenReturn(fitPenaltyFilteredResponse)

        val queryString = UriComponentsBuilder.newInstance()
            .queryParam("startDate", request.startDate)
            .queryParam("endDate", request.endDate)
            .queryParam("onlyPaid", request.onlyPaid)
            .queryParam("onlyNotPaid", request.onlyNotPaid)
            .queryParam("pageNumber", request.pageNumber)
            .queryParam("pageSize", request.pageSize)
            .build()
            .encode()
            .toUriString()
        //when
        val resultActions = mockMvc.perform(
            get(
                GlobalURI.FIT_PENALTY_FILTER_BY_USER + GlobalURI.PATH_VARIABLE_USER_ID_WITH_BRACE + queryString,
                requestUserId
            )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "fit-penalty-filter-by-user",
                    pathParameters(
                        parameterWithName(GlobalURI.PATH_VARIABLE_USER_ID)
                            .description("penalty list를 조회할 user id"),
                    ),
                    queryParameters(
                        parameterWithName("startDate")
                            .description("조회할 fit penalty 발생 날짜 시작 일자 ( 값 전송하지 않을시 이번달 조회 )"),
                        parameterWithName("endDate")
                            .description("조회할 fit penalty 발생 날짜 종료 일자 ( 값 전송하지 않을시 이번달 조회 )"),
                        parameterWithName("onlyPaid")
                            .description("입금 완료한 항목만 볼거라면 True 아니면 false 혹은 null (onlyNotPaid와 onlyPaid 둘다 true로 보내면 exception!!)"),
                        parameterWithName("onlyNotPaid")
                            .description("입금 미완료한 항목만 볼거라면 True 아니면 false 혹은 null (onlyNotPaid와 onlyPaid 둘다 true로 보내면 exception!!)"),
                        parameterWithName("pageNumber").description("조회할 fit penalty slice 페이지 번호 ( null일 경우 기본값 0 )"),
                        parameterWithName("pageSize").description("조회할 fit penalty slice size ( null일 경우 기본값 5 )"),
                    ),
                    responseFields(
                        fieldWithPath("pageNumber").type(JsonFieldType.NUMBER).description("조회 페이지 번호"),
                        fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("조회 한 페이지 size"),
                        fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 Slice가 있는지"),
                        fieldWithPath("totalAmount").type(JsonFieldType.NUMBER)
                            .description("총 금액 ( 결과 리스트 안의 Amount의 sum이 아닌 조회 조건으로 Paging 되지 않은 끝 Row의 Amount sum )"),
                        fieldWithPath("content[]").type(JsonFieldType.ARRAY)
                            .description("fit penalty 목록"),
                        fieldWithPath("content[].fitPenaltyId").type(JsonFieldType.NUMBER)
                            .description("fit penalty id"),
                        fieldWithPath("content[].fitGroupId").type(JsonFieldType.NUMBER)
                            .description("fit penalty가 발생한 fit group id"),
                        fieldWithPath("content[].userId").type(JsonFieldType.STRING)
                            .description("fit penalty (벌금) 이 발부된 user id"),
                        fieldWithPath("content[].amount").type(JsonFieldType.NUMBER)
                            .description("fit penalty 금액"),
                        fieldWithPath("content[].paid").type(JsonFieldType.BOOLEAN)
                            .description("fit penalty 납부 했는지 여부"),
                        fieldWithPath("content[].noNeedPay").type(JsonFieldType.BOOLEAN)
                            .description("fit penalty 납부할 필요 없는지 여부 ( ex-fit reader가 취소시켰을때 true )"),
                        fieldWithPath("content[].createdAt").type(JsonFieldType.STRING)
                            .description("fit penalty가 발생한 일시"),
                    )
                )
            )
    }
}