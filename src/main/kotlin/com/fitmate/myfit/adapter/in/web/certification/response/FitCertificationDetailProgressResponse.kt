package com.fitmate.myfit.adapter.`in`.web.certification.response

data class FitCertificationDetailProgressResponse(
    val fitCertificationId: Long,
    val agreeCount: Int,
    val disagreeCount: Int,
    val maxAgreeCount: Int
)
