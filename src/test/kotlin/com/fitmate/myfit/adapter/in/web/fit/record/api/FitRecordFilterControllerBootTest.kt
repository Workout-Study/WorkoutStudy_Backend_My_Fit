package com.fitmate.myfit.adapter.`in`.web.fit.record.api

import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.fit.record.request.FitRecordFilterRequest
import com.fitmate.myfit.adapter.`in`.web.fit.record.request.FitRecordSliceFilterRequest
import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordMultiMediaEndPointEntity
import com.fitmate.myfit.adapter.out.persistence.repository.FitRecordMultiMediaEndPointRepository
import com.fitmate.myfit.adapter.out.persistence.repository.FitRecordRepository
import org.junit.jupiter.api.BeforeEach
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
class FitRecordFilterControllerBootTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var fitRecordRepository: FitRecordRepository

    @Autowired
    private lateinit var multiMediaEndPointRepository: FitRecordMultiMediaEndPointRepository

    private val userId = "testUserId"
    private val recordEndStartDate: Instant =
        LocalDate.now().withDayOfMonth(1)
            .atStartOfDay().toInstant(ZoneOffset.UTC)
    private val recordEndEndDate: Instant =
        LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())
            .atStartOfDay().plusHours(23).plusMinutes(59).plusSeconds(59).toInstant(ZoneOffset.UTC)


    private val recordStartDate = Instant.now()
    private val recordEndDate = recordStartDate.plusSeconds(100000)
    private val multiMediaEndPoint = "https://avatars.githubusercontent.com/u/105261146?v=4"

    @BeforeEach
    fun setUp() {
        for (i in 1..5) {
            fitRecordRepository.save(
                FitRecordEntity(
                    userId,
                    recordStartDate.minusSeconds((i * 50000).toLong()),
                    recordEndDate.minusSeconds((i * 100000).toLong())
                )
            )
        }
    }

    @Test
    @DisplayName("[통합][Web Adapter] Fit record filter - 성공 테스트")
    @Throws(Exception::class)
    fun `filter fit record controller success test`() {
        //given
        val fitRecordFilterRequest = FitRecordFilterRequest(userId, recordEndStartDate, recordEndEndDate)

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
    }

    @Test
    @DisplayName("[통합][Web Adapter] Fit record filter empty result - 성공 테스트")
    @Throws(Exception::class)
    fun `filter fit record controller empty result success test`() {
        //given
        val fitRecordFilterRequest = FitRecordFilterRequest(userId)

        fitRecordRepository.deleteAll()

        val queryString = UriComponentsBuilder.newInstance()
            .queryParam("userId", fitRecordFilterRequest.userId)
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
    @DisplayName("[통합][Web Adapter] Fit record filter without date - 성공 테스트")
    @Throws(Exception::class)
    fun `filter fit record controller without date success test`() {
        //given
        val fitRecordEntity = fitRecordRepository.save(
            FitRecordEntity(
                userId,
                recordStartDate,
                recordEndDate
            )
        )

        multiMediaEndPointRepository.save(
            FitRecordMultiMediaEndPointEntity(fitRecordEntity, multiMediaEndPoint, userId)
        )

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
    @DisplayName("[통합][Web Adapter] Fit record slice filter - 성공 테스트")
    @Throws(Exception::class)
    fun `slice filter fit record controller success test`() {
        //given
        val fitRecordSliceFilterRequest =
            FitRecordSliceFilterRequest(userId, recordEndStartDate, recordEndEndDate, 0, 5)

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
    }

    @Test
    @DisplayName("[통합][Web Adapter] Fit record slice filter without condition - 성공 테스트")
    @Throws(Exception::class)
    fun `slice filter fit record controller without condition success test`() {
        //given
        val fitRecordSliceFilterRequest =
            FitRecordSliceFilterRequest(userId, null, null)

        val fitRecordEntity = fitRecordRepository.save(
            FitRecordEntity(
                userId,
                recordStartDate,
                recordEndDate
            )
        )

        multiMediaEndPointRepository.save(
            FitRecordMultiMediaEndPointEntity(fitRecordEntity, multiMediaEndPoint, userId)
        )

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

    @Test
    @DisplayName("[통합][Web Adapter] Fit record slice filter empty result - 성공 테스트")
    @Throws(Exception::class)
    fun `slice filter fit record controller empty result success test`() {
        //given
        val fitRecordSliceFilterRequest =
            FitRecordSliceFilterRequest(userId, null, null)

        fitRecordRepository.deleteAll()

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