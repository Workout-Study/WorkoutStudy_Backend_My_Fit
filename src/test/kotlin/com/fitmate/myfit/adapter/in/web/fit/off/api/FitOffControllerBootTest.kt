package com.fitmate.myfit.adapter.`in`.web.fit.off.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.fit.off.request.DeleteFitOffRequest
import com.fitmate.myfit.adapter.`in`.web.fit.off.request.RegisterFitOffRequest
import com.fitmate.myfit.adapter.`in`.web.fit.off.request.UpdateFitOffRequest
import com.fitmate.myfit.adapter.out.persistence.entity.FitOffEntity
import com.fitmate.myfit.adapter.out.persistence.repository.FitOffRepository
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
import java.time.LocalDate
import java.time.ZoneId

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = ["classpath:application-test.yml"])
class FitOffControllerBootTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var fitOffRepository: FitOffRepository

    private val requestUserId = 642
    private val fitOffStartDate = LocalDate.now().plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
    private val fitOffEndDate = LocalDate.now().plusDays(5).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
    private val fitOffReason = "발목 부상"

    @Test
    @DisplayName("[통합][Web Adapter] Fit off 등록 - 성공 테스트")
    @Throws(Exception::class)
    fun `register fit off controller success test`() {
        //given
        val registerFitOffRequest =
            RegisterFitOffRequest(requestUserId, fitOffStartDate, fitOffEndDate, fitOffReason)

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
    }

    @Test
    @DisplayName("[통합][Web Adapter] Fit off 삭제 - 성공 테스트")
    @Throws(Exception::class)
    fun `delete fit off controller success test`() {
        //given
        val deleteFitOffRequest = DeleteFitOffRequest(requestUserId)

        val fitOffEntity = fitOffRepository.save(
            FitOffEntity(
                requestUserId,
                fitOffStartDate,
                fitOffEndDate,
                fitOffReason
            )
        )

        //when
        val resultActions = mockMvc.perform(
            delete(
                GlobalURI.FIT_OFF_ROOT + GlobalURI.PATH_VARIABLE_FIT_OFF_ID_WITH_BRACE, fitOffEntity.id
            ).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteFitOffRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }

    @Test
    @DisplayName("[통합][Web Adapter] Fit off 수정 - 성공 테스트")
    @Throws(Exception::class)
    fun `update fit off controller success test`() {
        //given
        val updateFitOffRequest = UpdateFitOffRequest(requestUserId, fitOffStartDate, fitOffEndDate, fitOffReason)

        val fitOffEntity = fitOffRepository.save(
            FitOffEntity(
                requestUserId,
                fitOffStartDate,
                fitOffEndDate,
                fitOffReason
            )
        )

        //when
        val resultActions = mockMvc.perform(
            delete(
                GlobalURI.FIT_OFF_ROOT + GlobalURI.PATH_VARIABLE_FIT_OFF_ID_WITH_BRACE, fitOffEntity.id
            ).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateFitOffRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }
}