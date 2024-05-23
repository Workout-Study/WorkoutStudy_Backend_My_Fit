package com.fitmate.myfit.adapter.`in`.web.penalty.api

import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.penalty.request.FitPenaltyFilterByFitGroupRequest
import com.fitmate.myfit.adapter.`in`.web.penalty.request.FitPenaltyFilterByUserRequest
import com.fitmate.myfit.application.port.`in`.fit.penalty.response.FitPenaltyFilteredResponseDto
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.util.UriComponentsBuilder
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = ["classpath:application-test.yml"])
class FitPenaltyFilterControllerBootTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val startDate: Instant =
        LocalDate.now().withDayOfMonth(1)
            .atStartOfDay().toInstant(ZoneOffset.UTC)
    private val endDate: Instant =
        LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())
            .atStartOfDay().plusHours(23).plusMinutes(59).plusSeconds(59).toInstant(ZoneOffset.UTC)
    private val requestUserId = 642
    private val fitGroupId = 1634L

    private val pageNumber = 0
    private val pageSize = 5

    private val totalAmount = 15000

    @Test
    @DisplayName("[통합][Web Adapter] Fit penalty filter by user - 성공 테스트")
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
                    "testUserId" + i,
                    5000,
                    true,
                    false,
                    Instant.now()
                )
            )
        }

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
    }

    @Test
    @DisplayName("[통합][Web Adapter] Fit penalty filter by fit group - 성공 테스트")
    @Throws(Exception::class)
    fun `filter fit penalty by fit group controller success test`() {
        //given
        val request = FitPenaltyFilterByFitGroupRequest(
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
                    "testUserId" + i,
                    5000,
                    true,
                    false,
                    Instant.now()
                )
            )
        }

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
                GlobalURI.FIT_PENALTY_FILTER_BY_FIT_GROUP + GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE + queryString,
                fitGroupId
            )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }
}