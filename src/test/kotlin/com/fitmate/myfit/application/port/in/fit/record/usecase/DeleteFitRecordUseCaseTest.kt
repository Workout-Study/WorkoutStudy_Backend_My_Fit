package com.fitmate.myfit.application.port.`in`.fit.record.usecase

import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.application.port.`in`.certification.command.RegisterFitCertificationCommand
import com.fitmate.myfit.application.port.`in`.fit.record.command.DeleteFitRecordCommand
import com.fitmate.myfit.application.port.out.certification.ReadFitCertificationPort
import com.fitmate.myfit.application.port.out.fit.group.ReadFitGroupForReadPort
import com.fitmate.myfit.application.port.out.fit.record.*
import com.fitmate.myfit.application.service.service.FitRecordService
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
class DeleteFitRecordUseCaseTest {

    @InjectMocks
    private lateinit var deleteFitRecordUseCase: FitRecordService

    @Mock
    private lateinit var readFitRecordPort: ReadFitRecordPort

    @Mock
    private lateinit var registerFitRecordPort: RegisterFitRecordPort

    @Mock
    private lateinit var registerRecordMultiMediaEndPointPort: RegisterRecordMultiMediaEndPointPort

    @Mock
    private lateinit var readRecordMultiMediaEndPointPort: ReadRecordMultiMediaEndPointPort

    @Mock
    private lateinit var readFitCertificationPort: ReadFitCertificationPort

    @Mock
    private lateinit var updateFitRecordPort: UpdateFitRecordPort

    @Mock
    private lateinit var readFitGroupForReadPort: ReadFitGroupForReadPort

    @Mock
    private lateinit var updateFitRecordMultiMediaEndPointPort: UpdateRecordMultiMediaEndPointPort

    private val requestUserId = 642
    private val recordStartDate = Instant.now()
    private val recordEndDate = recordStartDate.plusSeconds(100000)
    private val multiMediaEndPoint: List<String> = listOf("https://avatars.githubusercontent.com/u/105261146?v=4")

    private val fitGroupIds = listOf(13L, 7L, 2L)

    private lateinit var fitRecordEntity: FitRecordEntity

    @BeforeEach
    fun setUp() {
        fitRecordEntity = FitRecordEntity(
            requestUserId,
            recordStartDate,
            recordEndDate
        )

        fitRecordEntity.id = 13
    }

    @Test
    @DisplayName("[단위][Use Case] Delete fit record - 성공 테스트")
    fun `delete fit record service success test`() {
        //given
        val deleteFitRecordCommand = DeleteFitRecordCommand(
            fitRecordEntity.id!!,
            requestUserId
        )

        whenever(readFitRecordPort.findById(any<Long>())).thenReturn(
            Optional.of(
                FitRecord.entityToDomain(
                    fitRecordEntity
                )
            )
        )
        whenever(
            readFitCertificationPort.findByFitRecordAndCertificationStatusNot(
                any<FitRecord>(),
                any<CertificationStatus>()
            )
        ).thenReturn(
            emptyList()
        )
        //when then
        Assertions.assertTrue(
            deleteFitRecordUseCase.deleteFitRecord(
                deleteFitRecordCommand
            ).isDeleteSuccess
        )
    }

    @Test
    @DisplayName("[단위][Use Case] Delete fit record does not exist - 실패 테스트")
    fun `delete fit record service fit record does not exist fail test`() {
        //given
        val deleteFitRecordCommand = DeleteFitRecordCommand(
            fitRecordEntity.id!!,
            requestUserId
        )

        whenever(readFitRecordPort.findById(any<Long>())).thenReturn(Optional.empty())
        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            deleteFitRecordUseCase.deleteFitRecord(
                deleteFitRecordCommand
            )
        }
    }

    @Test
    @DisplayName("[단위][Use Case] Delete fit record user does not matched - 실패 테스트")
    fun `delete fit record service user does not matched fail test`() {
        //given
        val deleteFitRecordCommand = DeleteFitRecordCommand(
            fitRecordEntity.id!!,
            requestUserId % 2
        )

        whenever(readFitRecordPort.findById(any<Long>())).thenReturn(
            Optional.of(
                FitRecord.entityToDomain(
                    fitRecordEntity
                )
            )
        )
        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            deleteFitRecordUseCase.deleteFitRecord(
                deleteFitRecordCommand
            )
        }
    }

    @Test
    @DisplayName("[단위][Use Case] Delete fit record already deleted - 실패 테스트")
    fun `delete fit record service already deleted fail test`() {
        //given
        val deleteFitRecordCommand = DeleteFitRecordCommand(
            fitRecordEntity.id!!,
            requestUserId
        )

        fitRecordEntity.delete()

        whenever(readFitRecordPort.findById(any<Long>())).thenReturn(
            Optional.of(
                FitRecord.entityToDomain(
                    fitRecordEntity
                )
            )
        )
        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            deleteFitRecordUseCase.deleteFitRecord(
                deleteFitRecordCommand
            )
        }
    }

    @Test
    @DisplayName("[단위][Use Case] Delete fit record already registered on fit certification - 실패 테스트")
    fun `delete fit record service already registered on fit certification fail test`() {
        //given
        val deleteFitRecordCommand = DeleteFitRecordCommand(
            fitRecordEntity.id!!,
            requestUserId
        )

        val registerFitCertificationCommand = RegisterFitCertificationCommand(
            requestUserId,
            fitRecordEntity.id!!,
            fitGroupIds
        )

        val fitCertificationList =
            FitCertification.createFitCertificationsByCommand(
                FitRecord.entityToDomain(fitRecordEntity),
                registerFitCertificationCommand
            )

        whenever(readFitRecordPort.findById(any<Long>())).thenReturn(
            Optional.of(
                FitRecord.entityToDomain(
                    fitRecordEntity
                )
            )
        )
        whenever(
            readFitCertificationPort.findByFitRecordAndCertificationStatusNot(
                any<FitRecord>(),
                any<CertificationStatus>()
            )
        ).thenReturn(
            fitCertificationList
        )
        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            deleteFitRecordUseCase.deleteFitRecord(
                deleteFitRecordCommand
            )
        }
    }
}