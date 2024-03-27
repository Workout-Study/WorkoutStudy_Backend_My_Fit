package com.fitmate.myfit.adapter.`in`.web.fit.record.api

import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.application.port.`in`.usecase.ReadFitRecordUseCase
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource
import org.springframework.beans.factory.annotation.Autowired
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
import org.springframework.web.util.UriComponentsBuilder
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

@WebMvcTest(FitRecordFilterController::class)
class FitRecordFilterControllerValidationTest {

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

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Web Adapter] Fit record filter user id validation - 실패 테스트")
    @Throws(Exception::class)
    fun `filter fit record controller user id validation fail test`(testUserId: String) {
        //given
        val queryString = UriComponentsBuilder.newInstance()
            .queryParam("userId", testUserId)
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
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }

    @ParameterizedTest
    @EmptySource
    @DisplayName("[단위][Web Adapter] Fit record slice filter user id validation - 실패 테스트")
    @Throws(Exception::class)
    fun `slice filter fit record controller user id validation fail test`(testUserId: String) {
        //given
        val queryString = UriComponentsBuilder.newInstance()
            .queryParam("userId", testUserId)
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
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }
}