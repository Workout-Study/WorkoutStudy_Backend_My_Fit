package com.fitmate.myfit.adapter.`in`.web.my.fit.api

import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.my.fit.request.FitCertificationProgressFilterRequest
import com.fitmate.myfit.adapter.`in`.web.my.fit.request.MyFitGroupIssueSliceFilterRequest
import com.fitmate.myfit.adapter.`in`.web.my.fit.request.NeedVoteCertificationFilterRequest
import com.fitmate.myfit.adapter.`in`.web.my.fit.response.MyFitGroupIssueSliceFilterResponse
import com.fitmate.myfit.adapter.out.api.SenderUtils
import com.fitmate.myfit.application.port.`in`.my.fit.command.FitCertificationProgressFilterCommand
import com.fitmate.myfit.application.port.`in`.my.fit.command.MyFitGroupIssueSliceFilterCommand
import com.fitmate.myfit.application.port.`in`.my.fit.command.NeedVoteCertificationFilterCommand
import com.fitmate.myfit.application.port.`in`.my.fit.response.FilterCertificationProgressResponseDto
import com.fitmate.myfit.application.port.`in`.my.fit.response.MyFitGroupIssueSliceFilterResponseDto
import com.fitmate.myfit.application.port.`in`.my.fit.response.NeedVoteCertificationFitGroupResponseDto
import com.fitmate.myfit.application.port.`in`.my.fit.response.NeedVoteCertificationResponseDto
import com.fitmate.myfit.application.port.`in`.my.fit.usecase.ReadFitCertificationProgressUseCase
import com.fitmate.myfit.application.port.`in`.my.fit.usecase.ReadFitGroupIssueUseCase
import com.fitmate.myfit.application.port.`in`.my.fit.usecase.ReadNeedVoteCertificationUseCase
import com.fitmate.myfit.domain.CertificationStatus
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
import java.time.Instant

@WebMvcTest(MyFitController::class)
@AutoConfigureRestDocs
class MyFitControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var readFitCertificationProgressUseCase: ReadFitCertificationProgressUseCase

    @MockBean
    private lateinit var readNeedVoteCertificationUseCase: ReadNeedVoteCertificationUseCase

    @MockBean
    private lateinit var readFitGroupIssueUseCase: ReadFitGroupIssueUseCase

    @MockBean
    private lateinit var senderUtils: SenderUtils

    private val requestUserId = 642
    private val fitGroupName = "헬창들은 일주일에 7번 운동해야죠 스터디"
    private val cycle = 1
    private val frequency = 7
    private val certificationCount = frequency - 3
    private val multiMediaEndPoint: List<String> = listOf("https://avatars.githubusercontent.com/u/105261146?v=4")
    private val maxFitMate = 20
    private val presentFitMateCount = 7
    private val userId = 12

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
                    maxFitMate,
                    presentFitMateCount,
                    multiMediaEndPoint[0],
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
                            .description("진척도 list"),
                        fieldWithPath("fitCertificationProgresses[].fitGroupId").type(JsonFieldType.NUMBER)
                            .description("fit group id"),
                        fieldWithPath("fitCertificationProgresses[].fitGroupName").type(JsonFieldType.STRING)
                            .description("fit group 이름"),
                        fieldWithPath("fitCertificationProgresses[].maxFitMate").type(JsonFieldType.NUMBER)
                            .description("fit group의 최대 fit mate 수"),
                        fieldWithPath("fitCertificationProgresses[].presentFitMateCount").type(JsonFieldType.NUMBER)
                            .description("fit group의 현재 fit mate 수"),
                        fieldWithPath("fitCertificationProgresses[].thumbnailEndPoint").type(JsonFieldType.STRING)
                            .description("fit group의 썸네일 사진 end point"),
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

    @Test
    @DisplayName("[단위][Web Adapter] Need vote certification list 조회 - 성공 테스트")
    @Throws(Exception::class)
    fun `get need vote certification list controller success test`() {
        //given
        val request = NeedVoteCertificationFilterRequest(requestUserId, 15L)

        val needVoteCertificationFitGroupResponseDtoList = mutableListOf<NeedVoteCertificationFitGroupResponseDto>()

        for (i in 1..3) {
            needVoteCertificationFitGroupResponseDtoList.add(
                NeedVoteCertificationFitGroupResponseDto(
                    i.toLong(),
                    fitGroupName,
                    listOf(
                        NeedVoteCertificationResponseDto(
                            i.toLong(),
                            i.toLong() + 1L,
                            Instant.now(),
                            Instant.now(),
                            requestUserId,
                            "testUserId" + 1,
                            i + 3,
                            i + 1,
                            i + 9,
                            Instant.now(),
                            multiMediaEndPoint
                        ),
                        NeedVoteCertificationResponseDto(
                            i.toLong() + 3L,
                            i.toLong() + 1L,
                            Instant.now(),
                            Instant.now(),
                            requestUserId,
                            "testUserId" + 2,
                            i + 1,
                            i + 0,
                            i + 6,
                            Instant.now(),
                            multiMediaEndPoint
                        ),
                    )
                )
            )
        }

        val queryString = UriComponentsBuilder.newInstance()
            .queryParam("requestUserId", request.requestUserId)
            .queryParam("fitGroupId", request.requestUserId)
            .build()
            .encode()
            .toUriString()

        whenever(readNeedVoteCertificationUseCase.filterNeedVoteCertification(any<NeedVoteCertificationFilterCommand>()))
            .thenReturn(needVoteCertificationFitGroupResponseDtoList)
        //when
        val resultActions = mockMvc.perform(
            get(GlobalURI.MY_FIT_NEED_VOTE_CERTIFICATION + queryString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "need-vote-certification",
                    queryParameters(
                        parameterWithName("requestUserId")
                            .description("Fit certification progress(인증 진척도)를 조회하는 User id"),
                        parameterWithName("fitGroupId")
                            .description("Fit certification progress(인증 진척도)를 조회할 fit group id ( 해당 값 안넘길경우 user가 포함된 fit group 전부를 대상으로 함 )")
                    ),
                    responseFields(
                        fieldWithPath("needVoteCertificationFitGroupList[]").type(JsonFieldType.ARRAY)
                            .description("유저가 투표해야할 인증 목록"),
                        fieldWithPath("needVoteCertificationFitGroupList[].fitGroupId").type(JsonFieldType.NUMBER)
                            .description("투표해야할 인증이 있는 fit group id"),
                        fieldWithPath("needVoteCertificationFitGroupList[].fitGroupName").type(JsonFieldType.STRING)
                            .description("투표해야할 인증이 있는 fit group의 이름"),
                        fieldWithPath("needVoteCertificationFitGroupList[].needVoteCertificationList[]").type(
                            JsonFieldType.ARRAY
                        ).description("fit group 내의 투표해야할 인증 목록"),
                        fieldWithPath("needVoteCertificationFitGroupList[].needVoteCertificationList[].certificationId").type(
                            JsonFieldType.NUMBER
                        ).description("투표해야할 인증의 id ( fit certification id )"),
                        fieldWithPath("needVoteCertificationFitGroupList[].needVoteCertificationList[].recordId").type(
                            JsonFieldType.NUMBER
                        ).description("투표해야할 인증의 recordId ( fit record id )"),
                        fieldWithPath("needVoteCertificationFitGroupList[].needVoteCertificationList[].recordStartDate").type(
                            JsonFieldType.STRING
                        ).description("투표해야할 인증의 record 시작 시간"),
                        fieldWithPath("needVoteCertificationFitGroupList[].needVoteCertificationList[].recordEndDate").type(
                            JsonFieldType.STRING
                        ).description("투표해야할 인증의 record 끝 시간)"),
                        fieldWithPath("needVoteCertificationFitGroupList[].needVoteCertificationList[].certificationRequestUserId").type(
                            JsonFieldType.NUMBER
                        ).description("투표해야할 인증을 요청한 유저 id"),
                        fieldWithPath("needVoteCertificationFitGroupList[].needVoteCertificationList[].certificationRequestUserNickname").type(
                            JsonFieldType.STRING
                        ).description("투표해야할 인증을 요청한 유저 nickname"),
                        fieldWithPath("needVoteCertificationFitGroupList[].needVoteCertificationList[].agreeCount").type(
                            JsonFieldType.NUMBER
                        ).description("찬성 수"),
                        fieldWithPath("needVoteCertificationFitGroupList[].needVoteCertificationList[].disagreeCount").type(
                            JsonFieldType.NUMBER
                        ).description("반대 수"),
                        fieldWithPath("needVoteCertificationFitGroupList[].needVoteCertificationList[].maxAgreeCount").type(
                            JsonFieldType.NUMBER
                        ).description("최대 투표 수"),
                        fieldWithPath("needVoteCertificationFitGroupList[].needVoteCertificationList[].voteEndDate").type(
                            JsonFieldType.STRING
                        ).description("투표 종료 일자"),
                        fieldWithPath("needVoteCertificationFitGroupList[].needVoteCertificationList[].recordMultiMediaEndPoints[]").type(
                            JsonFieldType.ARRAY
                        ).description("인증을 요청한 기록의 multi media end points")
                    )
                )
            )
    }

    @Test
    @DisplayName("[단위][Web Adapter] My Fit Group Issue Filter 조회 - 성공 테스트")
    @Throws(Exception::class)
    fun `get my fit group issue filter controller success test`() {
        //given
        val request = MyFitGroupIssueSliceFilterRequest(requestUserId)

        val myFitGroupIssueSliceFilterResponseDtoList = mutableListOf<MyFitGroupIssueSliceFilterResponseDto>()

        for (i in 1..5) {
            myFitGroupIssueSliceFilterResponseDtoList.add(
                MyFitGroupIssueSliceFilterResponseDto(
                    i.toLong(),
                    userId + i,
                    "user" + userId + i,
                    multiMediaEndPoint.get(0),
                    if (i % 2 == 0) CertificationStatus.REQUESTED else CertificationStatus.CERTIFIED,
                    i + 6,
                    i + 1,
                    i + 7,
                    if (i % 2 == 0) true else false,
                    if (i % 2 == 0) true else false,
                    Instant.now()
                )
            )
        }

        val resposne = MyFitGroupIssueSliceFilterResponse(
            request.pageNumber,
            request.pageSize,
            true,
            myFitGroupIssueSliceFilterResponseDtoList
        )

        val queryString = UriComponentsBuilder.newInstance()
            .queryParam("userId", request.userId)
            .queryParam("pageNumber", request.pageNumber)
            .queryParam("pageSize", request.pageSize)
            .build()
            .encode()
            .toUriString()

        whenever(readFitGroupIssueUseCase.filterFitGroupIssue(any<MyFitGroupIssueSliceFilterCommand>()))
            .thenReturn(resposne)
        //when
        val resultActions = mockMvc.perform(
            get(GlobalURI.MY_FIT_GROUP_ISSUE_FILTER + queryString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "fit-group-issue",
                    queryParameters(
                        parameterWithName("userId").description("내 그룹 소식을 조회하는 유저 id"),
                        parameterWithName("pageNumber").description("조회할 fit record slice 페이지 번호 ( null일 경우 기본값 0 )"),
                        parameterWithName("pageSize").description("조회할 fit record slice size ( null일 경우 기본값 5 )")
                    ),
                    responseFields(
                        fieldWithPath("pageNumber").type(JsonFieldType.NUMBER).description("조회 페이지 번호"),
                        fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("조회 한 페이지 size"),
                        fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("다음 Slice가 있는지"),
                        fieldWithPath("content[]").type(JsonFieldType.ARRAY).description("내 그룹 소식 list"),
                        fieldWithPath("content[].fitGroupId").type(JsonFieldType.NUMBER)
                            .description("인증이 요층 된 fit group id"),
                        fieldWithPath("content[].certificationRequestUserId").type(JsonFieldType.NUMBER)
                            .description("인증을 요청한 유저"),
                        fieldWithPath("content[].certificationRequestUserNickname").type(JsonFieldType.STRING)
                            .description("인증을 요청한 유저의 닉네임"),
                        fieldWithPath("content[].thumbnailEndPoint").type(JsonFieldType.STRING)
                            .description("인증의 썸네일 사진 end point"),
                        fieldWithPath("content[].certificationStatus").type(JsonFieldType.STRING)
                            .description("인증의 인증 상태 (REQUESTED, CERTIFIED, REJECTED)"),
                        fieldWithPath("content[].agreeCount").type(JsonFieldType.NUMBER)
                            .description("인증의 찬성 수"),
                        fieldWithPath("content[].disagreeCount").type(JsonFieldType.NUMBER)
                            .description("인증의 반대 수"),
                        fieldWithPath("content[].maxAgreeCount").type(JsonFieldType.NUMBER)
                            .description("인증의 최대 투표 수"),
                        fieldWithPath("content[].isUserVoteDone").type(JsonFieldType.BOOLEAN)
                            .description("조회를 요청한 유저가 인증에 투표 했는지 여부"),
                        fieldWithPath("content[].isUserAgree").type(JsonFieldType.BOOLEAN)
                            .description("조회를 요청한 유저가 인증에 찬성 했는지 여부"),
                        fieldWithPath("content[].issueDate").type(JsonFieldType.STRING)
                            .description("인증이 요청된 시각")
                    )
                )
            )
    }
}