package com.fitmate.myfit.application.port.`in`.certification.usecase

import com.fitmate.myfit.adapter.out.persistence.entity.FitCertificationEntity
import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.adapter.out.persistence.repository.FitCertificationRepository
import com.fitmate.myfit.adapter.out.persistence.repository.FitRecordRepository
import com.fitmate.myfit.application.port.`in`.certification.command.RegisterFitCertificationCommand
import com.fitmate.myfit.common.exceptions.BadRequestException
import com.fitmate.myfit.common.exceptions.ResourceAlreadyExistException
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
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@SpringBootTest
@Transactional
class RegisterFitCertificationUseCaseBootTest {

    @Autowired
    private lateinit var registerFitCertificationUseCase: RegisterFitCertificationUseCase

    @Autowired
    private lateinit var fitRecordRepository: FitRecordRepository

    @Autowired
    private lateinit var fitCertificationRepository: FitCertificationRepository

    private val requestUserId = "testUserId"
    private val fitGroupIds = listOf(13L, 7L, 2L)

    private val recordStartDate = Instant.now()
    private val recordEndDate = recordStartDate.plusSeconds(100000)
    private val multiMediaEndPoint: List<String> = listOf("https://avatars.githubusercontent.com/u/105261146?v=4")

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
    }

    @Test
    @DisplayName("[통합][Use Case] Register fit certification - 성공 테스트")
    fun `register fit certification service success test`() {
        //given
        val registerFitCertificationCommand = RegisterFitCertificationCommand(
            requestUserId,
            fitRecord.id!!,
            fitGroupIds
        )

        //when then
        Assertions.assertTrue(
            registerFitCertificationUseCase.registerFitCertification(registerFitCertificationCommand).isRegisterSuccess
        )
    }

    @Test
    @DisplayName("[통합][Use Case] Register fit certification fit record does not exist - 실패 테스트")
    fun `register fit certification service fit record does not exist fail test`() {
        //given
        val registerFitCertificationCommand = RegisterFitCertificationCommand(
            requestUserId,
            fitRecord.id!!,
            fitGroupIds
        )

        fitRecordRepository.deleteAll()

        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            registerFitCertificationUseCase.registerFitCertification(
                registerFitCertificationCommand
            )
        }
    }

    @Test
    @DisplayName("[통합][Use Case] Register fit certification already registered and is waiting result - 실패 테스트")
    fun `register fit certification service already registered and is waiting result fail test`() {
        //given
        val registerFitCertificationCommand = RegisterFitCertificationCommand(
            requestUserId,
            fitRecord.id!!,
            fitGroupIds
        )

        val fitCertification =
            FitCertification.createFitCertificationsByCommand(
                FitRecord.entityToDomain(fitRecord),
                registerFitCertificationCommand
            )

        fitCertification.forEach {
            fitCertificationRepository.save(FitCertificationEntity.domainToEntity(it))
        }

        //when then
        Assertions.assertThrows(ResourceAlreadyExistException::class.java) {
            registerFitCertificationUseCase.registerFitCertification(
                registerFitCertificationCommand
            )
        }
    }

    @Test
    @DisplayName("[통합][Use Case] Register fit certification already registered on same condition - 실패 테스트")
    fun `register fit certification service fit certification already already registered on same condition fail test`() {
        //given
        val registerFitCertificationCommand = RegisterFitCertificationCommand(
            requestUserId,
            fitRecord.id!!,
            fitGroupIds
        )

        val fitCertification =
            FitCertification.createFitCertificationsByCommand(
                FitRecord.entityToDomain(fitRecord),
                registerFitCertificationCommand
            )

        fitCertification.forEach {
            val fitCertificationEntity = FitCertificationEntity.domainToEntity(it)
            fitCertificationEntity.certificationStatus = CertificationStatus.CERTIFIED
            fitCertificationRepository.save(fitCertificationEntity)
        }

        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            registerFitCertificationUseCase.registerFitCertification(
                registerFitCertificationCommand
            )
        }
    }
}