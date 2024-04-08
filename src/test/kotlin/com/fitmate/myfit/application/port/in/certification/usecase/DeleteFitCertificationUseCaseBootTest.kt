package com.fitmate.myfit.application.port.`in`.certification.usecase

import com.fitmate.myfit.adapter.out.persistence.entity.FitCertificationEntity
import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.adapter.out.persistence.repository.FitCertificationRepository
import com.fitmate.myfit.adapter.out.persistence.repository.FitRecordRepository
import com.fitmate.myfit.application.port.`in`.certification.command.DeleteFitCertificationCommand
import com.fitmate.myfit.application.port.`in`.certification.command.RegisterFitCertificationCommand
import com.fitmate.myfit.common.exceptions.BadRequestException
import com.fitmate.myfit.common.exceptions.ResourceNotFoundException
import com.fitmate.myfit.domain.CertificationStatus
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
class DeleteFitCertificationUseCaseBootTest {

    @Autowired
    private lateinit var deleteFitCertificationUseCase: DeleteFitCertificationUseCase

    @Autowired
    private lateinit var fitRecordRepository: FitRecordRepository

    @Autowired
    private lateinit var fitCertificationRepository: FitCertificationRepository

    private val requestUserId = "testUserId"
    private val recordStartDate = Instant.now()
    private val fitGroupIds = listOf(13L, 7L, 2L)


    private lateinit var fitCertification: FitCertificationEntity
    private lateinit var fitRecord: FitRecordEntity

    @BeforeEach
    fun setUp() {
        fitRecord = fitRecordRepository.save(
            FitRecordEntity(
                requestUserId,
                Instant.now(),
                Instant.now().plusSeconds(100000L)
            )
        )

        val registerFitCertificationCommand =
            RegisterFitCertificationCommand(requestUserId, fitRecord.id!!, fitGroupIds)

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
    @DisplayName("[통합][Use Case] Delete fit certification - 성공 테스트")
    fun `delete fit certification service success test`() {
        //given
        val deleteFitCertificationCommand = DeleteFitCertificationCommand(
            fitCertification.id!!,
            requestUserId
        )

        //when then
        Assertions.assertTrue() {
            deleteFitCertificationUseCase.deleteFitCertification(
                deleteFitCertificationCommand
            ).isDeleteSuccess
        }
    }

    @Test
    @DisplayName("[통합][Use Case] Delete fit certification does not exist - 실패 테스트")
    fun `delete fit certification service does not exist fail test`() {
        //given
        val deleteFitCertificationCommand = DeleteFitCertificationCommand(
            -1,
            requestUserId
        )

        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            deleteFitCertificationUseCase.deleteFitCertification(
                deleteFitCertificationCommand
            )
        }
    }

    @Test
    @DisplayName("[통합][Use Case] Delete fit certification already deleted - 실패 테스트")
    fun `delete fit certification service already deleted fail test`() {
        //given
        val deleteFitCertificationCommand = DeleteFitCertificationCommand(
            fitCertification.id!!,
            requestUserId
        )

        fitCertification.delete()
        fitCertificationRepository.save(fitCertification)
        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            deleteFitCertificationUseCase.deleteFitCertification(
                deleteFitCertificationCommand
            )
        }
    }

    @Test
    @DisplayName("[통합][Use Case] Delete fit certification user not matched - 실패 테스트")
    fun `delete fit certification service user not matched fail test`() {
        //given
        val deleteFitCertificationCommand = DeleteFitCertificationCommand(
            fitCertification.id!!,
            "wrongRequestUserId"
        )

        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            deleteFitCertificationUseCase.deleteFitCertification(
                deleteFitCertificationCommand
            )
        }
    }

    @Test
    @DisplayName("[통합][Use Case] Delete fit certification already has result - 실패 테스트")
    fun `delete fit certification service already has result fail test`() {
        //given
        val deleteFitCertificationCommand = DeleteFitCertificationCommand(
            fitCertification.id!!,
            requestUserId
        )

        fitCertification.certificationStatus = CertificationStatus.REJECTED
        fitCertificationRepository.save(fitCertification)
        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            deleteFitCertificationUseCase.deleteFitCertification(
                deleteFitCertificationCommand
            )
        }
    }
}
