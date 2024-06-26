package com.fitmate.myfit.adapter.`in`.web.certification.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.myfit.adapter.`in`.web.certification.request.DeleteFitCertificationRequest
import com.fitmate.myfit.adapter.`in`.web.certification.request.RegisterFitCertificationRequest
import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.out.persistence.entity.FitCertificationEntity
import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.adapter.out.persistence.repository.FitCertificationRepository
import com.fitmate.myfit.adapter.out.persistence.repository.FitRecordRepository
import com.fitmate.myfit.application.port.`in`.certification.command.RegisterFitCertificationCommand
import com.fitmate.myfit.domain.FitCertification
import com.fitmate.myfit.domain.FitRecord
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
class FitCertificationControllerBootTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var fitRecordRepository: FitRecordRepository

    @Autowired
    private lateinit var fitCertificationRepository: FitCertificationRepository

    private val requestUserId = 642
    private val fitGroupIds = listOf(13L, 7L, 2L)

    private lateinit var fitRecord: FitRecordEntity
    private lateinit var fitCertification: FitCertificationEntity

    @BeforeEach
    fun setUp() {
        fitRecord = fitRecordRepository.save(
            FitRecordEntity(requestUserId, Instant.now(), Instant.now().plusSeconds(10000))
        )

        val registerFitCertificationCommand =
            RegisterFitCertificationCommand(requestUserId, fitRecord.id!!, listOf(1))

        fitCertification = fitCertificationRepository.save(
            FitCertificationEntity.domainToEntity(
                FitCertification.createFitCertificationsByCommand(
                    FitRecord.entityToDomain(fitRecord),
                    registerFitCertificationCommand
                )[0]
            )
        )
    }

    @Test
    @DisplayName("[통합][Web Adapter] Fit certification 등록 - 성공 테스트")
    @Throws(Exception::class)
    fun `register fit certification controller success test`() {
        //given
        val registerFitCertificationRequest =
            RegisterFitCertificationRequest(requestUserId, fitRecord.id!!, fitGroupIds)

        //when
        val resultActions = mockMvc.perform(
            post(GlobalURI.FIT_CERTIFICATION_ROOT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerFitCertificationRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isCreated())
            .andDo(print())
    }

    @Test
    @DisplayName("[통합][Web Adapter] Fit certification 삭제 - 성공 테스트")
    @Throws(Exception::class)
    fun `delete fit certification controller success test`() {
        //given
        val deleteFitCertificationRequest =
            DeleteFitCertificationRequest(requestUserId)

        //when
        val resultActions = mockMvc.perform(
            delete(
                GlobalURI.FIT_CERTIFICATION_ROOT + GlobalURI.PATH_VARIABLE_FIT_CERTIFICATION_ID_WITH_BRACE,
                fitCertification.id
            )
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteFitCertificationRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }
}