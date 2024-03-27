package com.fitmate.myfit.common.exceptions

class BadRequestException(override val message: String) : RuntimeException(message) {
}