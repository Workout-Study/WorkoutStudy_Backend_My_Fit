package com.fitmate.myfit.adapter.`in`.web.fit.record.api

import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.fit.record.request.FitRecordFilterRequest
import com.fitmate.myfit.adapter.`in`.web.fit.record.request.FitRecordSliceFilterRequest
import com.fitmate.myfit.adapter.`in`.web.fit.record.response.FitCertificationResponse
import com.fitmate.myfit.application.port.`in`.fit.record.command.FitRecordFilterCommand
import com.fitmate.myfit.application.port.`in`.fit.record.command.FitRecordSliceFilterCommand
import com.fitmate.myfit.application.port.`in`.fit.record.response.FitRecordDetailResponseDto
import com.fitmate.myfit.application.port.`in`.fit.record.usecase.ReadFitRecordUseCase
import com.fitmate.myfit.domain.CertificationStatus
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.SliceImpl
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
import java.time.temporal.ChronoUnit

@WebMvcTest(FitRecordFilterController::class)
@AutoConfigureRestDocs
class FitRecordFilterControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var readFitRecordUseCase: ReadFitRecordUseCase

    private val userId = "testUserId"
    private val recordEndStartDate: Instant =
        LocalDate.now().withDayOfMonth(1)
            .atStartOfDay().toInstant(ZoneOffset.UTC)
    private val recordEndEndDate: Instant =
        LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())
            .atStartOfDay().plusHours(23).plusMinutes(59).plusSeconds(59).toInstant(ZoneOffset.UTC)


    private val recordStartDate = Instant.now()
    private val recordEndDate = recordStartDate.plusSeconds(100000)
    private val multiMediaEndPoint: List<String> = listOf("https://avatars.githubusercontent.com/u/105261146?v=4")
    private val fitCertifications: List<FitCertificationResponse> =
        listOf(
            FitCertificationResponse(
                5L,
                "헬짱짱",
                23L,
                CertificationStatus.REQUESTED,
                Instant.now(),
                Instant.now().plus(12, ChronoUnit.HOURS)
            ),
            FitCertificationResponse(
                767L,
                "정일헬짱짱 모임",
                28L,
                CertificationStatus.CERTIFIED,
                Instant.now(),
                Instant.now().plus(12, ChronoUnit.HOURS)
            )
        )

    @Test
    @DisplayName("[단위][Web Adapter] Fit record filter - 성공 테스트")
    @Throws(Exception::class)
    fun `filter fit record controller success test`() {
        //given
        val fitRecordFilterRequest = FitRecordFilterRequest(userId, recordEndStartDate, recordEndEndDate)

        val fitRecordFilterResponseList = mutableListOf<FitRecordDetailResponseDto>()

        for (i in 0..3) {
            fitRecordFilterResponseList.add(
                FitRecordDetailResponseDto(
                    i.toLong(),
                    recordStartDate,
                    recordEndDate,
                    Instant.now(),
                    multiMediaEndPoint,
                    fitCertifications
                )
            )
        }

        whenever(readFitRecordUseCase.filterFitRecord(any<FitRecordFilterCommand>()))
            .thenReturn(fitRecordFilterResponseList)

        val queryString = UriComponentsBuilder.newInstance()
            .queryParam("userId", fitRecordFilterRequest.userId)
            .queryParam("recordEndStartDate", fitRecordFilterRequest.recordEndStartDate)
            .queryParam("recordEndEndDate", fitRecordFilterRequest.recordEndEndDate)
            .build()
            .encode()
            .toUriString()
        //when
        val resultActions = mockMvc.perform(
            get(GlobalURI.FIT_RECORD_FILTER + queryString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "fit-record-filter",
                    queryParameters(
                        parameterWithName("userId")
                            .description("조회할 record를 기록한 user id"),
                        parameterWithName("recordEndStartDate")
                            .description("조회할 fit record의 기록 종료 시간 시작시간 ( 값 전송하지 않을시 이번달 조회 )"),
                        parameterWithName("recordEndEndDate")
                            .description("조회할 fit record의 기록 종료 시간 종료시간 ( 값 전송하지 않을시 이번달 조회 )")
                    ),
                    responseFields(
                        fieldWithPath("fitRecordDetailResponseDtoList[]").type(JsonFieldType.ARRAY)
                            .description("fit record list"),
                        fieldWithPath("fitRecordDetailResponseDtoList[].fitRecordId").type(JsonFieldType.NUMBER)
                            .description("fit record의 id"),
                        fieldWithPath("fitRecordDetailResponseDtoList[].recordStartDate").type(JsonFieldType.STRING)
                            .description("fit record의 기록 시작시간"),
                        fieldWithPath("fitRecordDetailResponseDtoList[].recordEndDate").type(JsonFieldType.STRING)
                            .description("fit record의 기록 종료시간"),
                        fieldWithPath("fitRecordDetailResponseDtoList[].createdAt").type(JsonFieldType.STRING)
                            .description("fit record의 생성일"),
                        fieldWithPath("fitRecordDetailResponseDtoList[].multiMediaEndPoints").type(JsonFieldType.ARRAY)
                            .description("fit record의 멀티 미디어 end point list"),
                        fieldWithPath("fitRecordDetailResponseDtoList[].registeredFitCertifications[]").type(
                            JsonFieldType.ARRAY
                        )
                            .description("fit record가 등록돼있는 fit certification 목록 ( fit group 포함 )"),
                        fieldWithPath("fitRecordDetailResponseDtoList[].registeredFitCertifications[].fitGroupId").type(
                            JsonFieldType.NUMBER
                        )
                            .description("인증이 등록돼있는 fit group id"),
                        fieldWithPath("fitRecordDetailResponseDtoList[].registeredFitCertifications[].fitGroupName").type(
                            JsonFieldType.STRING
                        )
                            .description("인증이 등록돼있는 fit group의 이름"),
                        fieldWithPath("fitRecordDetailResponseDtoList[].registeredFitCertifications[].fitCertificationId").type(
                            JsonFieldType.NUMBER
                        )
                            .description("fit record가 인증으로 등록 돼 있는 fit certification id"),
                        fieldWithPath("fitRecordDetailResponseDtoList[].registeredFitCertifications[].certificationStatus").type(
                            JsonFieldType.STRING
                        )
                            .description("인증 상태 (REQUESTED, CERTIFIED, REJECTED)"),
                        fieldWithPath("fitRecordDetailResponseDtoList[].registeredFitCertifications[].createdAt").type(
                            JsonFieldType.STRING
                        )
                            .description("인증 등록 일시"),
                        fieldWithPath("fitRecordDetailResponseDtoList[].registeredFitCertifications[].voteEndDate").type(
                            JsonFieldType.STRING
                        )
                            .description("인증 종료 일시"),
                    )
                )
            )
    }

    @Test
    @DisplayName("[단위][Web Adapter] Fit record filter without date - 성공 테스트")
    @Throws(Exception::class)
    fun `filter fit record controller without date success test`() {
        //given
        val fitRecordFilterResponseList = mutableListOf<FitRecordDetailResponseDto>()

        for (i in 0..3) {
            fitRecordFilterResponseList.add(
                FitRecordDetailResponseDto(
                    i.toLong(),
                    recordStartDate,
                    recordEndDate,
                    Instant.now(),
                    multiMediaEndPoint,
                    fitCertifications
                )
            )
        }

        whenever(readFitRecordUseCase.filterFitRecord(any<FitRecordFilterCommand>()))
            .thenReturn(fitRecordFilterResponseList)

        val queryString = UriComponentsBuilder.newInstance()
            .queryParam("userId", userId)
            .build()
            .encode()
            .toUriString()
        //when
        val resultActions = mockMvc.perform(
            get(GlobalURI.FIT_RECORD_FILTER + queryString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }

    @Test
    @DisplayName("[단위][Web Adapter] Fit record slice filter - 성공 테스트")
    @Throws(Exception::class)
    fun `slice filter fit record controller success test`() {
        //given
        val fitRecordSliceFilterRequest =
            FitRecordSliceFilterRequest(userId, recordEndStartDate, recordEndEndDate, 0, 5)

        val fitRecordFilterResponseList = mutableListOf<FitRecordDetailResponseDto>()

        for (i in 0..3) {
            fitRecordFilterResponseList.add(
                FitRecordDetailResponseDto(
                    i.toLong(),
                    recordStartDate,
                    recordEndDate,
                    Instant.now(),
                    multiMediaEndPoint,
                    fitCertifications
                )
            )
        }

        val pageRequest = PageRequest.of(0, 5)
        val fitRecordSliceFilterResponse = SliceImpl(fitRecordFilterResponseList, pageRequest, true)

        whenever(readFitRecordUseCase.sliceFilterFitRecord(any<FitRecordSliceFilterCommand>()))
            .thenReturn(fitRecordSliceFilterResponse)

        val queryString = UriComponentsBuilder.newInstance()
            .queryParam("userId", fitRecordSliceFilterRequest.userId)
            .queryParam("recordEndStartDate", fitRecordSliceFilterRequest.recordEndStartDate)
            .queryParam("recordEndEndDate", fitRecordSliceFilterRequest.recordEndEndDate)
            .queryParam("pageNumber", fitRecordSliceFilterRequest.pageNumber)
            .queryParam("pageSize", fitRecordSliceFilterRequest.pageSize)
            .build()
            .encode()
            .toUriString()
        //when
        val resultActions = mockMvc.perform(
            get(GlobalURI.FIT_RECORD_SLICE_FILTER + queryString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
            .andDo(
                document(
                    "fit-record-slice-filter",
                    queryParameters(
                        parameterWithName("userId")
                            .description("조회할 record를 기록한 user id"),
                        parameterWithName("recordEndStartDate")
                            .description("조회할 fit record의 기록 종료 시간 시작시간 ( 값 전송하지 않을시 전체 조회 )"),
                        parameterWithName("recordEndEndDate")
                            .description("조회할 fit record의 기록 종료 시간 종료시간 ( 값 전송하지 않을시 전체 조회 )"),
                        parameterWithName("pageNumber").description("조회할 fit record slice 페이지 번호 ( null일 경우 기본값 0 )"),
                        parameterWithName("pageSize").description("조회할 fit record slice size ( null일 경우 기본값 5 )"),
                    ),
                    responseFields(
                        fieldWithPath("content[]").type(JsonFieldType.ARRAY)
                            .description("fit record list"),
                        fieldWithPath("content[].fitRecordId").type(JsonFieldType.NUMBER)
                            .description("fit record의 id"),
                        fieldWithPath("content[].recordStartDate").type(JsonFieldType.STRING)
                            .description("fit record의 기록 시작시간"),
                        fieldWithPath("content[].recordEndDate").type(JsonFieldType.STRING)
                            .description("fit record의 기록 종료시간"),
                        fieldWithPath("content[].createdAt").type(JsonFieldType.STRING)
                            .description("fit record의 생성일"),
                        fieldWithPath("content[].multiMediaEndPoints").type(JsonFieldType.ARRAY)
                            .description("fit record의 멀티 미디어 end point list"),
                        fieldWithPath("content[].registeredFitCertifications[]").type(JsonFieldType.ARRAY)
                            .description("fit record가 등록돼있는 fit certification 목록 ( fit group 포함 )"),
                        fieldWithPath("content[].registeredFitCertifications[].fitGroupId").type(JsonFieldType.NUMBER)
                            .description("인증이 등록돼있는 fit group id"),
                        fieldWithPath("content[].registeredFitCertifications[].fitGroupName").type(JsonFieldType.STRING)
                            .description("인증이 등록돼있는 fit group의 이름"),
                        fieldWithPath("content[].registeredFitCertifications[].fitCertificationId").type(JsonFieldType.NUMBER)
                            .description("fit record가 인증으로 등록 돼 있는 fit certification id"),
                        fieldWithPath("content[].registeredFitCertifications[].certificationStatus").type(JsonFieldType.STRING)
                            .description("인증 상태 (REQUESTED, CERTIFIED, REJECTED)"),
                        fieldWithPath("content[].registeredFitCertifications[].createdAt").type(JsonFieldType.STRING)
                            .description("인증 등록 일시"),
                        fieldWithPath("content[].registeredFitCertifications[].voteEndDate").type(JsonFieldType.STRING)
                            .description("인증 종료 일시"),
                        fieldWithPath("pageable").type(JsonFieldType.OBJECT).description("pageable object"),
                        fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description("조회 페이지 번호"),
                        fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description("조회 한 size"),
                        fieldWithPath("pageable.sort").type(JsonFieldType.OBJECT).description("sort object"),
                        fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("sort 요청 여부"),
                        fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("sort 여부"),
                        fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("unsort 여부"),
                        fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description("대상 시작 번호"),
                        fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description("unpaged"),
                        fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description("paged"),
                        fieldWithPath("size").type(JsonFieldType.NUMBER).description("List 크기"),
                        fieldWithPath("number").type(JsonFieldType.NUMBER).description("조회 페이지 번호"),
                        fieldWithPath("sort").type(JsonFieldType.OBJECT).description("sort object"),
                        fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("sort 요청 여부"),
                        fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("sort 여부"),
                        fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("unsort 여부"),
                        fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements"),
                        fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("처음인지 여부"),
                        fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("마지막인지 여부"),
                        fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("비어있는지 여부")
                    )
                )
            )
    }

    @Test
    @DisplayName("[단위][Web Adapter] Fit record slice filter without condition - 성공 테스트")
    @Throws(Exception::class)
    fun `slice filter fit record controller without condition success test`() {
        //given
        val fitRecordSliceFilterRequest =
            FitRecordSliceFilterRequest(userId, null, null)

        val fitRecordFilterResponseList = mutableListOf<FitRecordDetailResponseDto>()

        for (i in 0..3) {
            fitRecordFilterResponseList.add(
                FitRecordDetailResponseDto(
                    i.toLong(),
                    recordStartDate,
                    recordEndDate,
                    Instant.now(),
                    multiMediaEndPoint,
                    fitCertifications
                )
            )
        }

        val pageRequest = PageRequest.of(0, 5)
        val fitRecordSliceFilterResponse = SliceImpl(fitRecordFilterResponseList, pageRequest, true)

        whenever(readFitRecordUseCase.sliceFilterFitRecord(any<FitRecordSliceFilterCommand>()))
            .thenReturn(fitRecordSliceFilterResponse)

        val queryString = UriComponentsBuilder.newInstance()
            .queryParam("userId", fitRecordSliceFilterRequest.userId)
            .build()
            .encode()
            .toUriString()
        //when
        val resultActions = mockMvc.perform(
            get(GlobalURI.FIT_RECORD_SLICE_FILTER + queryString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }
}