package com.fitmate.myfit.adapter.`in`.web.common

class GlobalURI {

    companion object {
        const val ROOT_URI = "/fit-group-service"

        const val FIT_RECORD_ROOT = "$ROOT_URI/records"
        const val FIT_RECORD_FILTER = "$FIT_RECORD_ROOT/filters"
        const val FIT_RECORD_SLICE_FILTER = "$FIT_RECORD_FILTER/slice"

        const val FIT_CERTIFICATION_ROOT = "$ROOT_URI/certifications"

        const val PATH_VARIABLE_FIT_CERTIFICATION_ID = "fit-certification-id"
        const val PATH_VARIABLE_FIT_CERTIFICATION_ID_WITH_BRACE = "/{$PATH_VARIABLE_FIT_CERTIFICATION_ID}"
    }
}