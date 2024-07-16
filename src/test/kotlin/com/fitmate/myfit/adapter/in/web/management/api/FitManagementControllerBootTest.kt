package com.fitmate.myfit.adapter.`in`.web.management.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.management.request.NoNeedPayFitPenaltyRequest
import com.fitmate.myfit.adapter.`in`.web.management.request.PaidFitPenaltyRequest
import com.fitmate.myfit.adapter.out.persistence.entity.FitGroupForReadEntity
import com.fitmate.myfit.adapter.out.persistence.entity.FitPenaltyEntity
import com.fitmate.myfit.adapter.out.persistence.repository.FitGroupForReadRepository
import com.fitmate.myfit.adapter.out.persistence.repository.FitPenaltyRepository
import com.fitmate.myfit.application.port.`in`.fit.group.command.SaveFitGroupForReadCommand
import com.fitmate.myfit.application.port.`in`.fit.penalty.command.RegisterFitPenaltyCommand
import com.fitmate.myfit.application.service.dto.FitGroupResponseDto
import com.fitmate.myfit.application.service.dto.FitPenaltyDetailResponseDto
import com.fitmate.myfit.common.GlobalStatus
import com.fitmate.myfit.domain.FitGroupForRead
import com.fitmate.myfit.domain.FitPenalty
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
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = ["classpath:application-test.yml"])
class FitManagementControllerBootTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var fitPenaltyRepository: FitPenaltyRepository

    @Autowired
    private lateinit var fitGroupForReadRepository: FitGroupForReadRepository

    private val fitPenaltyId = 162L
    private val requestUserId = 642
    private val fitGroupId = 63L
    private val maxFitMate = 20
    private val presentFitMateCount = 7

    private lateinit var fitPenaltyEntity: FitPenaltyEntity

    @BeforeEach
    fun setUp() {
        val fitGroupForRead = fitGroupForReadRepository.save(
            FitGroupForReadEntity.domainToEntity(
                FitGroupForRead.createByFitGroupDetail(
                    FitGroupResponseDto(
                        fitGroupId,
                        "testFitGroupName",
                        requestUserId,
                        maxFitMate,
                        presentFitMateCount,
                        1,
                        3,
                        listOf(),
                        GlobalStatus.PERSISTENCE_NOT_DELETED
                    ),
                    SaveFitGroupForReadCommand(fitGroupId, "test")
                )
            )
        )

        fitPenaltyEntity = fitPenaltyRepository.save(
            FitPenaltyEntity.domainToEntity(
                FitPenalty.createByFitPenaltyDetail(
                    FitPenaltyDetailResponseDto(
                        fitPenaltyId,
                        fitGroupId,
                        requestUserId,
                        5000,
                        Instant.now()
                    ),
                    RegisterFitPenaltyCommand(
                        fitPenaltyId,
                        "test"
                    )
                )
            )
        )
    }

    @Test
    @DisplayName("[통합][Web Adapter] Fit management penalty 입금 완료 by fit leader - 성공 테스트")
    @Throws(Exception::class)
    fun `fit penalty paid by fit leader controller success test`() {
        //given
        val paidFitPenaltyRequest = PaidFitPenaltyRequest(requestUserId)

        //when
        val resultActions = mockMvc.perform(
            put(GlobalURI.FIT_PENALTY_MANAGEMENT + GlobalURI.PATH_VARIABLE_FIT_PENALTY_ID_WITH_BRACE, fitPenaltyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paidFitPenaltyRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }

    @Test
    @DisplayName("[통합][Web Adapter] Fit management penalty 입금 불필요 처리 by fit leader - 성공 테스트")
    @Throws(Exception::class)
    fun `fit penalty no need pay by fit leader controller success test`() {
        //given
        val noNeedPayFitPenaltyRequest = NoNeedPayFitPenaltyRequest(requestUserId)

        //when
        val resultActions = mockMvc.perform(
            delete(GlobalURI.FIT_PENALTY_MANAGEMENT + GlobalURI.PATH_VARIABLE_FIT_PENALTY_ID_WITH_BRACE, fitPenaltyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(noNeedPayFitPenaltyRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }
}