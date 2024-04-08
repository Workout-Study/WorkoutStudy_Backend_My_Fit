package com.fitmate.myfit.application.port.`in`.certification.usecase

import com.fitmate.myfit.adapter.out.persistence.entity.FitCertificationEntity
import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.application.port.`in`.certification.command.DeleteFitCertificationCommand
import com.fitmate.myfit.application.port.`in`.certification.command.RegisterFitCertificationCommand
import com.fitmate.myfit.application.port.out.certification.ReadFitCertificationPort
import com.fitmate.myfit.application.port.out.certification.RegisterFitCertificationPort
import com.fitmate.myfit.application.port.out.certification.UpdateFitCertificationPort
import com.fitmate.myfit.application.port.out.fit.record.ReadFitRecordPort
import com.fitmate.myfit.application.service.service.FitCertificationService
import com.fitmate.myfit.common.exceptions.BadRequestException
import com.fitmate.myfit.common.exceptions.ResourceNotFoundException
import com.fitmate.myfit.domain.CertificationStatus
import com.fitmate.myfit.domain.FitCertification
import com.fitmate.myfit.domain.FitRecord
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.time.Instant
import java.util.*

@ExtendWith(MockitoExtension::class)
class DeleteFitCertificationUseCaseTest {

    @InjectMocks
    private lateinit var deleteFitCertificationUseCase: FitCertificationService

    @Mock
    private lateinit var readFitRecordPort: ReadFitRecordPort

    @Mock
    private lateinit var readFitCertificationPort: ReadFitCertificationPort

    @Mock
    private lateinit var registerFitCertificationPort: RegisterFitCertificationPort

    @Mock
    private lateinit var updateFitCertificationPort: UpdateFitCertificationPort

    private val requestUserId = "testUserId"
    private val recordStartDate = Instant.now()
    private val recordEndDate = recordStartDate.plusSeconds(100000)
    private val fitGroupIds = listOf(13L, 7L, 2L)

    private val fitRecordEntity = FitRecordEntity(
        requestUserId,
        recordStartDate,
        recordEndDate
    )

    private lateinit var fitCertification: FitCertificationEntity

    @BeforeEach
    fun setUp() {
        fitRecordEntity.id = 13

        val registerFitCertificationCommand =
            RegisterFitCertificationCommand(requestUserId, fitRecordEntity.id!!, fitGroupIds)

        fitCertification = FitCertificationEntity.domainToEntity(
            FitCertification.createFitCertificationsByCommand(
                FitRecord.entityToDomain(fitRecordEntity),
                registerFitCertificationCommand
            )[0]
        )

        fitCertification.id = 137L
    }

    @Test
    @DisplayName("[단위][Use Case] Delete fit certification - 성공 테스트")
    fun `delete fit certification service success test`() {
        //given
        val deleteFitCertificationCommand = DeleteFitCertificationCommand(
            fitCertification.id!!,
            requestUserId
        )

        whenever(readFitCertificationPort.findById(any<Long>())).thenReturn(
            Optional.of(
                FitCertification.entityToDomain(
                    fitCertification
                )
            )
        )
        //when then
        Assertions.assertTrue(
            deleteFitCertificationUseCase.deleteFitCertification(
                deleteFitCertificationCommand
            ).isDeleteSuccess
        )
    }

    @Test
    @DisplayName("[단위][Use Case] Delete fit certification does not exist - 실패 테스트")
    fun `delete fit certification service does not exist fail test`() {
        //given
        val deleteFitCertificationCommand = DeleteFitCertificationCommand(
            fitCertification.id!!,
            requestUserId
        )

        whenever(readFitCertificationPort.findById(any<Long>())).thenReturn(
            Optional.empty()
        )
        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            deleteFitCertificationUseCase.deleteFitCertification(
                deleteFitCertificationCommand
            )
        }
    }

    @Test
    @DisplayName("[단위][Use Case] Delete fit certification already deleted - 실패 테스트")
    fun `delete fit certification service already deleted fail test`() {
        //given
        val deleteFitCertificationCommand = DeleteFitCertificationCommand(
            fitCertification.id!!,
            requestUserId
        )

        fitCertification.delete()

        whenever(readFitCertificationPort.findById(any<Long>())).thenReturn(
            Optional.of(FitCertification.entityToDomain(fitCertification))
        )
        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            deleteFitCertificationUseCase.deleteFitCertification(
                deleteFitCertificationCommand
            )
        }
    }

    @Test
    @DisplayName("[단위][Use Case] Delete fit certification user not matched - 실패 테스트")
    fun `delete fit certification service user not matched fail test`() {
        //given
        val deleteFitCertificationCommand = DeleteFitCertificationCommand(
            fitCertification.id!!,
            requestUserId
        )

        val wrongUserId = "wrongUserId"

        val fitRecordEntity = FitRecordEntity(
            wrongUserId,
            recordStartDate,
            recordEndDate
        )

        fitRecordEntity.id = 1928L

        val registerFitCertificationCommand =
            RegisterFitCertificationCommand(wrongUserId, fitRecordEntity.id!!, fitGroupIds)

        val otherFitCertification = FitCertificationEntity.domainToEntity(
            FitCertification.createFitCertificationsByCommand(
                FitRecord.entityToDomain(fitRecordEntity),
                registerFitCertificationCommand
            )[0]
        )

        whenever(readFitCertificationPort.findById(any<Long>())).thenReturn(
            Optional.of(FitCertification.entityToDomain(otherFitCertification))
        )
        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            deleteFitCertificationUseCase.deleteFitCertification(
                deleteFitCertificationCommand
            )
        }
    }

    @Test
    @DisplayName("[단위][Use Case] Delete fit certification already has result - 실패 테스트")
    fun `delete fit certification service already has result fail test`() {
        //given
        val deleteFitCertificationCommand = DeleteFitCertificationCommand(
            fitCertification.id!!,
            requestUserId
        )

        fitCertification.certificationStatus = CertificationStatus.REJECTED

        whenever(readFitCertificationPort.findById(any<Long>())).thenReturn(
            Optional.of(FitCertification.entityToDomain(fitCertification))
        )
        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            deleteFitCertificationUseCase.deleteFitCertification(
                deleteFitCertificationCommand
            )
        }
    }
}
