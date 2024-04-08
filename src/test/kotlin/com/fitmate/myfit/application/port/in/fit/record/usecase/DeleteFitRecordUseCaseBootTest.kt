package com.fitmate.myfit.application.port.`in`.fit.record.usecase

import com.fitmate.myfit.adapter.out.persistence.entity.FitCertificationEntity
import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.adapter.out.persistence.repository.FitCertificationRepository
import com.fitmate.myfit.adapter.out.persistence.repository.FitRecordRepository
import com.fitmate.myfit.application.port.`in`.certification.command.RegisterFitCertificationCommand
import com.fitmate.myfit.application.port.`in`.fit.record.command.DeleteFitRecordCommand
import com.fitmate.myfit.common.exceptions.BadRequestException
import com.fitmate.myfit.common.exceptions.ResourceNotFoundException
import com.fitmate.myfit.domain.FitCertification
import com.fitmate.myfit.domain.FitRecord
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@SpringBootTest
@Transactional
@TestPropertySource(locations = ["classpath:application-test.yml"])
class DeleteFitRecordUseCaseBootTest {

    @Autowired
    private lateinit var deleteFitRecordUseCase: DeleteFitRecordUseCase

    @Autowired
    private lateinit var fitRecordRepository: FitRecordRepository

    @Autowired
    private lateinit var fitCertificationRepository: FitCertificationRepository

    private val requestUserId = "testUserId"
    private val recordStartDate = Instant.now()
    private val recordEndDate = recordStartDate.plusSeconds(100000)

    private val fitGroupIds = listOf(13L, 7L, 2L)

    private lateinit var fitRecord: FitRecordEntity

    @BeforeEach
    fun setUp() {
        fitRecord = fitRecordRepository.save(
            FitRecordEntity(
                requestUserId,
                recordStartDate,
                recordEndDate
            )
        )
    }

    @Test
    @DisplayName("[통합][Use Case] Delete fit record - 성공 테스트")
    fun `delete fit record service success test`() {
        //given
        val deleteFitRecordCommand = DeleteFitRecordCommand(
            fitRecord.id!!,
            requestUserId
        )

        //when then
        Assertions.assertTrue(
            deleteFitRecordUseCase.deleteFitRecord(
                deleteFitRecordCommand
            ).isDeleteSuccess
        )
    }

    @Test
    @DisplayName("[통합][Use Case] Delete fit record does not exist - 실패 테스트")
    fun `delete fit record service fit record does not exist fail test`() {
        //given
        val deleteFitRecordCommand = DeleteFitRecordCommand(
            -1,
            requestUserId
        )

        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            deleteFitRecordUseCase.deleteFitRecord(
                deleteFitRecordCommand
            )
        }
    }

    @Test
    @DisplayName("[통합][Use Case] Delete fit record user does not matched - 실패 테스트")
    fun `delete fit record service user does not matched fail test`() {
        //given
        val deleteFitRecordCommand = DeleteFitRecordCommand(
            fitRecord.id!!,
            "wrongRequestUserId"
        )

        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            deleteFitRecordUseCase.deleteFitRecord(
                deleteFitRecordCommand
            )
        }
    }

    @Test
    @DisplayName("[통합][Use Case] Delete fit record already registered on fit certification - 실패 테스트")
    fun `delete fit record service already registered on fit certification fail test`() {
        //given
        val deleteFitRecordCommand = DeleteFitRecordCommand(
            fitRecord.id!!,
            requestUserId
        )

        val registerFitCertificationCommand = RegisterFitCertificationCommand(
            requestUserId,
            fitRecord.id!!,
            fitGroupIds
        )

        val fitCertificationList =
            FitCertification.createFitCertificationsByCommand(
                FitRecord.entityToDomain(fitRecord),
                registerFitCertificationCommand
            )

        val fitCertification = FitCertificationEntity.domainToEntity(fitCertificationList[0])
        fitCertificationRepository.save(fitCertification)

        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            deleteFitRecordUseCase.deleteFitRecord(
                deleteFitRecordCommand
            )
        }
    }
}