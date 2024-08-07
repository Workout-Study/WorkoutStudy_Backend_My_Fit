package com.fitmate.myfit.adapter.`in`.web.common

class GlobalURI {

    companion object {
        const val ROOT_URI = "/my-fit-service"

        const val FIT_RECORD_ROOT = "$ROOT_URI/records"
        const val FIT_RECORD_FILTER = "$FIT_RECORD_ROOT/filters"
        const val FIT_RECORD_SLICE_FILTER = "$FIT_RECORD_FILTER/slice"

        const val FIT_CERTIFICATION_ROOT = "$ROOT_URI/certifications"
        const val FIT_CERTIFICATION_FILTER = "$FIT_CERTIFICATION_ROOT/filters"
        const val FIT_CERTIFICATION_PROGRESS = "$FIT_CERTIFICATION_ROOT/progresses"

        const val PATH_VARIABLE_FIT_CERTIFICATION_ID = "fit-certification-id"
        const val PATH_VARIABLE_FIT_CERTIFICATION_ID_WITH_BRACE = "/{$PATH_VARIABLE_FIT_CERTIFICATION_ID}"

        const val PATH_VARIABLE_FIT_RECORD_ID = "fit-record-id"
        const val PATH_VARIABLE_FIT_RECORD_ID_WITH_BRACE = "/{$PATH_VARIABLE_FIT_RECORD_ID}"

        const val PATH_VARIABLE_FIT_GROUP_ID = "fit-group-id"
        const val PATH_VARIABLE_FIT_GROUP_ID_WITH_BRACE = "/{$PATH_VARIABLE_FIT_GROUP_ID}"

        const val PATH_VARIABLE_USER_ID = "user-id"
        const val PATH_VARIABLE_USER_ID_WITH_BRACE = "/{$PATH_VARIABLE_USER_ID}"

        const val VOTE_ROOT = "$ROOT_URI/votes"

        const val MY_FIT_ROOT = "$ROOT_URI/my-fits"
        const val MY_FIT_CERTIFICATION = "$MY_FIT_ROOT/certifications"
        const val MY_FIT_CERTIFICATION_PROGRESS = "$MY_FIT_CERTIFICATION/progresses"

        const val MY_FIT_NEED_VOTE_CERTIFICATION = "$MY_FIT_CERTIFICATION/need-votes"

        const val MY_FIT_GROUP_ISSUE_FILTER = "$MY_FIT_ROOT/issue"

        const val FIT_PENALTY_ROOT = "$ROOT_URI/penalties"
        const val FIT_PENALTY_FILTER = "$FIT_PENALTY_ROOT/filters"
        const val FIT_PENALTY_FILTER_BY_USER = "$FIT_PENALTY_FILTER/by-users"
        const val FIT_PENALTY_FILTER_BY_FIT_GROUP = "$FIT_PENALTY_FILTER/by-fit-group"

        const val FIT_MANAGEMENT_ROOT = "$ROOT_URI/management"
        const val FIT_PENALTY_MANAGEMENT = "$FIT_MANAGEMENT_ROOT/penalties"

        const val PATH_VARIABLE_FIT_PENALTY_ID = "fit-penalty-id"
        const val PATH_VARIABLE_FIT_PENALTY_ID_WITH_BRACE = "/{$PATH_VARIABLE_FIT_PENALTY_ID}"

        const val FIT_OFF_ROOT = "$ROOT_URI/fit-offs"

        const val PATH_VARIABLE_FIT_OFF_ID = "fit-off-id"
        const val PATH_VARIABLE_FIT_OFF_ID_WITH_BRACE = "/{$PATH_VARIABLE_FIT_OFF_ID}"
    }
}