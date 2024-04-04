package com.fitmate.myfit.adapter.`in`.web.common

class GlobalURI {

    companion object {
        const val ROOT_URI = "/my-fit-service"

        const val FIT_RECORD_ROOT = "$ROOT_URI/records"
        const val FIT_RECORD_FILTER = "$FIT_RECORD_ROOT/filters"
        const val FIT_RECORD_SLICE_FILTER = "$FIT_RECORD_FILTER/slice"

        const val FIT_CERTIFICATION_ROOT = "$ROOT_URI/certifications"

        const val PATH_VARIABLE_FIT_CERTIFICATION_ID = "fit-certification-id"
        const val PATH_VARIABLE_FIT_CERTIFICATION_ID_WITH_BRACE = "/{$PATH_VARIABLE_FIT_CERTIFICATION_ID}"

        const val PATH_VARIABLE_FIT_RECORD_ID = "fit-record-id"
        const val PATH_VARIABLE_FIT_RECORD_ID_WITH_BRACE = "/{$PATH_VARIABLE_FIT_RECORD_ID}"

        const val VOTE_ROOT = "$ROOT_URI/votes"

        const val MY_FIT_ROOT = "$ROOT_URI/my-fits"
        const val MY_FIT_CERTIFICATION = "$MY_FIT_ROOT/certifications"
        const val MY_FIT_CERTIFICATION_PROGRESS = "$MY_FIT_CERTIFICATION/progresses"

        const val MY_FIT_NEED_VOTE_CERTIFICATION = "$MY_FIT_CERTIFICATION/need-votes"
    }
}