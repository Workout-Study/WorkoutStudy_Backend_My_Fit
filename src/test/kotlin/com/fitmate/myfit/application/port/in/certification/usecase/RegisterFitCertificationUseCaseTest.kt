package com.fitmate.myfit.application.port.`in`.certification.usecase

import com.fitmate.myfit.adapter.out.persistence.entity.FitCertificationEntity
import com.fitmate.myfit.adapter.out.persistence.entity.FitRecordEntity
import com.fitmate.myfit.application.port.`in`.certification.command.RegisterFitCertificationCommand
import com.fitmate.myfit.application.port.`in`.fit.record.command.RegisterFitRecordCommand
import com.fitmate.myfit.application.port.out.certification.*
import com.fitmate.myfit.application.port.out.fit.group.ReadFitGroupForReadPort
import com.fitmate.myfit.application.port.out.fit.mate.ReadFitMateForReadPort
import com.fitmate.myfit.application.port.out.fit.record.ReadFitRecordPort
import com.fitmate.myfit.application.port.out.fit.record.ReadRecordMultiMediaEndPointPort
import com.fitmate.myfit.application.port.out.vote.ReadVotePort
import com.fitmate.myfit.application.service.service.FitCertificationService
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
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.time.Instant
import java.util.*

@ExtendWith(MockitoExtension::class)
class RegisterFitCertificationUseCaseTest {

    @InjectMocks
    private lateinit var registerFitCertificationUseCase: FitCertificationService

    @Mock
    private lateinit var readFitRecordPort: ReadFitRecordPort

    @Mock
    private lateinit var readFitCertificationPort: ReadFitCertificationPort

    @Mock
    private lateinit var registerFitCertificationPort: RegisterFitCertificationPort

    @Mock
    private lateinit var updateFitCertificationPort: UpdateFitCertificationPort

    @Mock
    private lateinit var readFitGroupForReadPort: ReadFitGroupForReadPort

    @Mock
    private lateinit var readFitMateForReadPort: ReadFitMateForReadPort

    @Mock
    private lateinit var fitCertificationEventPublishPort: FitCertificationEventPublishPort

    @Mock
    private lateinit var readVotePort: ReadVotePort

    @Mock
    private lateinit var readFitCertificationResultPort: ReadFitCertificationResultPort

    @Mock
    private lateinit var readVoteMultiMediaEndPointPort: ReadRecordMultiMediaEndPointPort

    private val requestUserId = "testUserId"
    private val fitGroupIds = listOf(13L, 7L, 2L)

    private val recordStartDate = Instant.now()
    private val recordEndDate = recordStartDate.plusSeconds(100000)
    private val multiMediaEndPoint: List<String> = listOf("https://avatars.githubusercontent.com/u/105261146?v=4")

    private val registerFitRecordCommand = RegisterFitRecordCommand(
        requestUserId,
        recordStartDate,
        recordEndDate,
        multiMediaEndPoint
    )
    private val fitRecordEntity = FitRecordEntity(
        requestUserId,
        recordStartDate,
        recordEndDate
    )

    @BeforeEach
    fun setUp() {
        fitRecordEntity.id = 1L
    }

    @Test
    @DisplayName("[단위][Use Case] Register fit certification - 성공 테스트")
    fun `register fit certification service success test`() {
        //given
        val registerFitCertificationCommand = RegisterFitCertificationCommand(
            requestUserId,
            fitRecordEntity.id!!,
            fitGroupIds
        )

        val fitRecord = FitRecord.createByCommand(registerFitRecordCommand)

        val fitCertificationList = FitCertification.createFitCertificationsByCommand(
            fitRecord,
            registerFitCertificationCommand
        )

        val fitCertificationEntity = FitCertificationEntity.domainToEntity(fitCertificationList[0])
        fitCertificationEntity.id = 13

        whenever(readFitRecordPort.findById(any<Long>())).thenReturn(Optional.of(fitRecord))
        whenever(
            readFitCertificationPort.findByUserIdAndFitGroupIdAndCertificationStatus(
                any<String>(),
                any<Long>(),
                any<CertificationStatus>()
            )
        ).thenReturn(Optional.empty())
        whenever(
            readFitCertificationPort.findByFitRecordAndFitGroupIdAndCertificationStatusNot(
                any<FitRecord>(),
                any<Long>(),
                any<CertificationStatus>()
            )
        ).thenReturn(emptyList())
        whenever(
            registerFitCertificationPort.registerFitCertification(
                any<FitCertification>()
            )
        ).thenReturn(FitCertification.entityToDomain(fitCertificationEntity))
        //when then
        Assertions.assertTrue(
            registerFitCertificationUseCase.registerFitCertification(registerFitCertificationCommand).isRegisterSuccess
        )
    }

    @Test
    @DisplayName("[단위][Use Case] Register fit certification fit record does not exist - 실패 테스트")
    fun `register fit certification service fit record does not exist fail test`() {
        //given
        val registerFitCertificationCommand = RegisterFitCertificationCommand(
            requestUserId,
            fitRecordEntity.id!!,
            fitGroupIds
        )

        whenever(readFitRecordPort.findById(any<Long>())).thenReturn(Optional.empty())
        //when then
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            registerFitCertificationUseCase.registerFitCertification(
                registerFitCertificationCommand
            )
        }
    }

    @Test
    @DisplayName("[단위][Use Case] Register fit certification fit record already deleted - 실패 테스트")
    fun `register fit certification service fit record already deleted fail test`() {
        //given
        val registerFitCertificationCommand = RegisterFitCertificationCommand(
            requestUserId,
            fitRecordEntity.id!!,
            fitGroupIds
        )

        val fitRecord = FitRecord.createByCommand(
            RegisterFitRecordCommand(
                requestUserId,
                recordStartDate,
                recordEndDate,
                multiMediaEndPoint
            )
        )

        fitRecord.delete()

        whenever(readFitRecordPort.findById(any<Long>())).thenReturn(Optional.of(fitRecord))
        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            registerFitCertificationUseCase.registerFitCertification(
                registerFitCertificationCommand
            )
        }
    }

    @Test
    @DisplayName("[단위][Use Case] Register fit certification already registered and is waiting result - 실패 테스트")
    fun `register fit certification service already registered and is waiting result fail test`() {
        //given
        val registerFitCertificationCommand = RegisterFitCertificationCommand(
            requestUserId,
            fitRecordEntity.id!!,
            fitGroupIds
        )

        val fitRecord = FitRecord.createByCommand(
            RegisterFitRecordCommand(
                requestUserId,
                recordStartDate,
                recordEndDate,
                multiMediaEndPoint
            )
        )

        val fitCertification =
            FitCertification.createFitCertificationsByCommand(fitRecord, registerFitCertificationCommand)

        whenever(readFitRecordPort.findById(any<Long>())).thenReturn(Optional.of(fitRecord))
        whenever(
            readFitCertificationPort.findByUserIdAndFitGroupIdAndCertificationStatus(
                any<String>(),
                any<Long>(),
                any<CertificationStatus>()
            )
        ).thenReturn(Optional.of(fitCertification[0]))
        //when then
        Assertions.assertThrows(ResourceAlreadyExistException::class.java) {
            registerFitCertificationUseCase.registerFitCertification(
                registerFitCertificationCommand
            )
        }
    }

    @Test
    @DisplayName("[단위][Use Case] Register fit certification already registered on same condition - 실패 테스트")
    fun `register fit certification service fit certification already already registered on same condition fail test`() {
        //given
        val registerFitCertificationCommand = RegisterFitCertificationCommand(
            requestUserId,
            fitRecordEntity.id!!,
            fitGroupIds
        )

        val fitRecord = FitRecord.createByCommand(
            RegisterFitRecordCommand(
                requestUserId,
                recordStartDate,
                recordEndDate,
                multiMediaEndPoint
            )
        )

        val fitCertification =
            FitCertification.createFitCertificationsByCommand(fitRecord, registerFitCertificationCommand)

        whenever(readFitRecordPort.findById(any<Long>())).thenReturn(Optional.of(fitRecord))
        whenever(
            readFitCertificationPort.findByUserIdAndFitGroupIdAndCertificationStatus(
                any<String>(),
                any<Long>(),
                any<CertificationStatus>()
            )
        ).thenReturn(Optional.empty())
        whenever(
            readFitCertificationPort.findByFitRecordAndFitGroupIdAndCertificationStatusNot(
                any<FitRecord>(),
                any<Long>(),
                any<CertificationStatus>()
            )
        ).thenReturn(fitCertification)
        //when then
        Assertions.assertThrows(BadRequestException::class.java) {
            registerFitCertificationUseCase.registerFitCertification(
                registerFitCertificationCommand
            )
        }
    }
}