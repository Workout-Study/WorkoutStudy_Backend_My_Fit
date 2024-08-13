package com.fitmate.myfit.adapter.out.api.uris

class BatchServiceURI {

    companion object {
        const val FIT_MATE_BATCH_ROOT_URI = "http://fit-mate-batch:8010/batch-service"

        const val FIT_CERTIFICATION_RESULT_ROOT = "$FIT_MATE_BATCH_ROOT_URI/certifications/results"

        const val FIT_PENALTY_ROOT = "$FIT_MATE_BATCH_ROOT_URI/penalties"
    }
}