package com.fitmate.myfit.adapter.`in`.web.certification.api

import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.out.persistence.entity.FitGroupForReadEntity
import com.fitmate.myfit.adapter.out.persistence.repository.FitGroupForReadRepository
import com.fitmate.myfit.application.port.`in`.fit.group.command.SaveFitGroupForReadCommand
import com.fitmate.myfit.application.service.dto.FitGroupResponseDto
import com.fitmate.myfit.common.GlobalStatus
import com.fitmate.myfit.domain.FitGroupForRead
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

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = ["classpath:application-test.yml"])
class FitCertificationFilterControllerBootTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var fitGroupForeReadRepository: FitGroupForReadRepository

    private val requestUserId = 642

    private val fitGroupId = 1131L
    private val fitGroupName = "헬짱이 될테야"
    private val cycle = 1
    private val frequency = 3
    private val multiMediaEndPoint: List<String> = listOf("https://avatars.githubusercontent.com/u/105261146?v=4")

    private lateinit var fitGroupForRead: FitGroupForReadEntity

    @BeforeEach
    fun setUp() {
        fitGroupForRead = fitGroupForeReadRepository.save(
            FitGroupForReadEntity.domainToEntity(
                FitGroupForRead.createByFitGroupDetail(
                    FitGroupResponseDto(
                        fitGroupId,
                        fitGroupName,
                        requestUserId,
                        cycle,
                        frequency,
                        multiMediaEndPoint,
                        GlobalStatus.PERSISTENCE_NOT_DELETED
                    ),
                    SaveFitGroupForReadCommand(fitGroupId, "test")
                )
            )
        )
    }

    @Test
    @DisplayName("[통합][Web Adapter] Fit certification by fit group list 조회 - 성공 테스트")
    @Throws(Exception::class)
    fun `get fit certification list by fit group controller success test`() {
        //given

        //when
        val resultActions = mockMvc.perform(
            get(
                GlobalURI.FIT_CERTIFICATION_FILTER
                        + GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE
                        + GlobalURI.PATH_VARIABLE_USER_ID_WITH_BRACE,
                fitGroupId,
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
    @DisplayName("[통합][Web Adapter] Fit certification progress by fit group 조회 - 성공 테스트")
    @Throws(Exception::class)
    fun `get fit certification progress by fit group controller success test`() {
        //given
        //when
        val resultActions = mockMvc.perform(
            get(
                GlobalURI.MY_FIT_CERTIFICATION_PROGRESS
                        + GlobalURI.PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE, fitGroupId
            ).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }
}