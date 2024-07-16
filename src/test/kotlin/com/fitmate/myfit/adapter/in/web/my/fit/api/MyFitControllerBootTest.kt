package com.fitmate.myfit.adapter.`in`.web.my.fit.api

import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.adapter.`in`.web.my.fit.request.FitCertificationProgressFilterRequest
import com.fitmate.myfit.adapter.`in`.web.my.fit.request.MyFitGroupIssueSliceFilterRequest
import com.fitmate.myfit.adapter.`in`.web.my.fit.request.NeedVoteCertificationFilterRequest
import com.fitmate.myfit.adapter.out.persistence.entity.FitCertificationEntity
import com.fitmate.myfit.adapter.out.persistence.entity.FitGroupForReadEntity
import com.fitmate.myfit.adapter.out.persistence.entity.FitMateForReadEntity
import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.adapter.out.persistence.repository.FitCertificationRepository
import com.fitmate.myfit.adapter.out.persistence.repository.FitGroupForReadRepository
import com.fitmate.myfit.adapter.out.persistence.repository.FitMateForReadRepository
import com.fitmate.myfit.adapter.out.persistence.repository.FitRecordRepository
import com.fitmate.myfit.application.port.`in`.certification.command.RegisterFitCertificationCommand
import com.fitmate.myfit.application.port.`in`.fit.group.command.SaveFitGroupForReadCommand
import com.fitmate.myfit.application.port.`in`.fit.mate.command.SaveFitMateForReadCommand
import com.fitmate.myfit.application.service.dto.FitGroupResponseDto
import com.fitmate.myfit.application.service.dto.FitMateResponseDto
import com.fitmate.myfit.domain.*
import org.junit.jupiter.api.Assertions.*
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
import org.springframework.web.util.UriComponentsBuilder
import java.time.Instant

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = ["classpath:application-test.yml"])
class MyFitControllerBootTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var fitGroupForReadRepository: FitGroupForReadRepository

    @Autowired
    private lateinit var fitMateForReadRepository: FitMateForReadRepository

    @Autowired
    private lateinit var fitRecordRepository: FitRecordRepository

    @Autowired
    private lateinit var fitCertificationRepository: FitCertificationRepository

    private val requestUserId = 642
    private val fitGroupName = "헬창들은 일주일에 7번 운동해야죠 스터디"
    private val cycle = 1
    private val frequency = 7

    private val recordStartDate = Instant.now()
    private val recordEndDate = recordStartDate.plusSeconds(100000)
    private val multiMediaEndPoint: List<String> = listOf("https://avatars.githubusercontent.com/u/105261146?v=4")
    private val maxFitMate = 20
    private val presentFitMateCount = 7

    @BeforeEach
    fun setUp() {
        val fitGroupIds = mutableListOf<Long>()

        for (i in -1 downTo -5) {
            val fitGroupForRead = fitGroupForReadRepository.save(
                FitGroupForReadEntity.domainToEntity(
                    FitGroupForRead.createByFitGroupDetail(
                        FitGroupResponseDto(
                            i.toLong(),
                            fitGroupName + i,
                            i,
                            maxFitMate,
                            presentFitMateCount,
                            cycle,
                            frequency + i,
                            multiMediaEndPoint,
                            false
                        ),
                        SaveFitGroupForReadCommand(
                            i.toLong(),
                            "test"
                        )
                    )
                )
            )

            fitMateForReadRepository.save(
                FitMateForReadEntity.domainToEntity(
                    FitMateForRead.createByDetail(
                        fitGroupForRead.fitGroupId,
                        FitMateResponseDto(
                            i.toLong(),
                            requestUserId
                        ),
                        SaveFitMateForReadCommand(
                            fitGroupForRead.fitGroupId,
                            "test"
                        )
                    )
                )
            )

            fitGroupIds.add(fitGroupForRead.fitGroupId)
        }

        for (i in 1..2) {
            val fitRecord = fitRecordRepository.save(
                FitRecordEntity(
                    requestUserId,
                    recordStartDate.minusSeconds((i * 50000).toLong()),
                    recordEndDate.minusSeconds((i * 100000).toLong())
                )
            )

            val fitCertificationCommand = RegisterFitCertificationCommand(
                requestUserId,
                fitRecord.id!!,
                fitGroupIds
            )

            val fitCertificationList = FitCertification.createFitCertificationsByCommand(
                FitRecord.entityToDomain(fitRecord),
                fitCertificationCommand
            )
            fitCertificationList.forEach {
                it.certificationStatus = CertificationStatus.CERTIFIED
                fitCertificationRepository.save(FitCertificationEntity.domainToEntity(it))
            }

            val otherFitCertificationCommand = RegisterFitCertificationCommand(
                i + 99,
                fitRecord.id!!,
                fitGroupIds
            )

            val otherFitCertificationList = FitCertification.createFitCertificationsByCommand(
                FitRecord.entityToDomain(fitRecord),
                otherFitCertificationCommand
            )
            otherFitCertificationList.forEach {
                it.certificationStatus = CertificationStatus.CERTIFIED
                fitCertificationRepository.save(FitCertificationEntity.domainToEntity(it))
            }
        }
    }

    @Test
    @DisplayName("[통합][Web Adapter] Fit certification progress list 조회 - 성공 테스트")
    @Throws(Exception::class)
    fun `get fit certification progress list controller success test`() {
        //given
        val request = FitCertificationProgressFilterRequest(requestUserId)

        val queryString = UriComponentsBuilder.newInstance()
            .queryParam("requestUserId", request.requestUserId)
            .build()
            .encode()
            .toUriString()

        //when
        val resultActions = mockMvc.perform(
            get(GlobalURI.MY_FIT_CERTIFICATION_PROGRESS + queryString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }

    @Test
    @DisplayName("[통합][Web Adapter] Need vote certification list 조회 - 성공 테스트")
    @Throws(Exception::class)
    fun `get need vote certification list controller success test`() {
        //given
        val request = NeedVoteCertificationFilterRequest(requestUserId)

        val queryString = UriComponentsBuilder.newInstance()
            .queryParam("requestUserId", request.requestUserId)
            .build()
            .encode()
            .toUriString()

        //when
        val resultActions = mockMvc.perform(
            get(GlobalURI.MY_FIT_NEED_VOTE_CERTIFICATION + queryString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }

    @Test
    @DisplayName("[통합][Web Adapter] My Fit Group Issue Filter 조회 - 성공 테스트")
    @Throws(Exception::class)
    fun `get my fit group issue filter controller success test`() {
        //given
        val request = MyFitGroupIssueSliceFilterRequest(requestUserId)

        val queryString = UriComponentsBuilder.newInstance()
            .queryParam("userId", request.userId)
            .build()
            .encode()
            .toUriString()

        //when
        val resultActions = mockMvc.perform(
            get(GlobalURI.MY_FIT_GROUP_ISSUE_FILTER + queryString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isOk())
            .andDo(print())
    }
}