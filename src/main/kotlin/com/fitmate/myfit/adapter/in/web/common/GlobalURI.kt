package com.fitmate.myfit.adapter.`in`.web.common

class GlobalURI {

    companion object {
        const val ROOT_URI = "/fit-group-service"

        const val FIT_RECORD_ROOT = "$ROOT_URI/records"
        const val FIT_RECORD_FILTER = "$FIT_RECORD_ROOT/filters"
        const val FIT_RECORD_SLICE_FILTER = "$FIT_RECORD_FILTER/slice"
    }
}