package com.fitmate.myfit.adapter.`in`.web.fit.record.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.fit.record.request.DeleteFitRecordRequest
import com.fitmate.myfit.adapter.`in`.web.fit.record.request.RegisterFitRecordRequest
import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.adapter.out.persistence.repository.FitRecordRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class FitRecordControllerBootTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var fitRecordRepository: FitRecordRepository;

    private val requestUserId = "testUserId"
    private val recordStartDate = Instant.now()
    private val recordEndDate = recordStartDate.plusSeconds(100000)
    private val multiMediaEndPoint: List<String> = listOf("https://avatars.githubusercontent.com/u/105261146?v=4")

    @Test
    @DisplayName("[통합][Web Adapter] Fit record 등록 - 성공 테스트")
    @Throws(Exception::class)
    fun `register fit record controller success test`() {
        //given
        val registerFitRecordRequest =
            RegisterFitRecordRequest(requestUserId, recordStartDate, recordEndDate, multiMediaEndPoint)

        //when
        val resultActions = mockMvc.perform(
            post(GlobalURI.FIT_RECORD_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerFitRecordRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isCreated())
            .andDo(print())
    }

    @Test
    @DisplayName("[통합][Web Adapter] Fit record 삭제 - 성공 테스트")
    @Throws(Exception::class)
    fun `delete fit record controller success test`() {
        //given
        val deleteFitRecordRequest = DeleteFitRecordRequest(requestUserId)

        val fitRecordEntity = fitRecordRepository.save(
            FitRecordEntity(
                requestUserId,
                Instant.now().minusSeconds(50000L),
                Instant.now()
            )
        )

        //when
        val resultActions = mockMvc.perform(
            delete(
                GlobalURI.FIT_RECORD_ROOT + GlobalURI.PATH_VARIABLE_FIT_RECORD_ID_WITH_BRACE, fitRecordEntity.id!!
            ).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteFitRecordRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }
}