package com.fitmate.myfit.adapter.`in`.web.certification.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fitmate.myfit.adapter.`in`.web.certification.request.DeleteFitCertificationRequest
import com.fitmate.myfit.adapter.`in`.web.common.GlobalURI
import com.fitmate.myfit.application.port.`in`.certification.usecase.DeleteFitCertificationUseCase
import com.fitmate.myfit.application.port.`in`.certification.usecase.ReadFitCertificationUseCase
import com.fitmate.myfit.application.port.`in`.certification.usecase.RegisterFitCertificationUseCase
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(FitCertificationController::class)
class FitCertificationControllerValidationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var registerFitCertificationUseCase: RegisterFitCertificationUseCase

    @MockBean
    private lateinit var deleteFitCertificationUseCase: DeleteFitCertificationUseCase

    @MockBean
    private lateinit var readFitCertificationUseCase: ReadFitCertificationUseCase

    private val requestUserId = 642
    private val fitRecordId = 137L
    private val fitGroupIds = listOf(13L, 7L, 2L)

    @Test
    @DisplayName("[단위][Web Adapter] Fit certification 삭제 fit certification id validation - 실패 테스트")
    @Throws(Exception::class)
    fun `delete fit certification controller fit certification id validation fail test`() {
        //given
        val deleteFitCertificationRequest =
            DeleteFitCertificationRequest(requestUserId)

        val badFitCertificationId = "badFitCertificationId"

        //when
        val resultActions = mockMvc.perform(
            delete(
                GlobalURI.FIT_CERTIFICATION_ROOT + GlobalURI.PATH_VARIABLE_FIT_CERTIFICATION_ID_WITH_BRACE,
                badFitCertificationId
            )
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteFitCertificationRequest))
                .accept(MediaType.APPLICATION_JSON)
        )
        //then
        resultActions.andExpect(status().isBadRequest())
            .andDo(print())
    }
}