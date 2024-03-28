package com.fitmate.myfit.common.exceptions

class ResourceNotFoundException(override val message: String) : RuntimeException(message) {
}