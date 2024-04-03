package com.fitmate.myfit.common

class GlobalStatus {

    companion object {
        const val PERSISTENCE_NOT_DELETED: Boolean = false
        const val PERSISTENCE_DELETED: Boolean = true

        const val KAFKA_GROUP_ID = "fit-group-service"
        const val KAFKA_TOPIC_FIT_GROUP = "fit-group"
        const val KAFKA_TOPIC_FIT_MATE = "fit-mate"
    }
}