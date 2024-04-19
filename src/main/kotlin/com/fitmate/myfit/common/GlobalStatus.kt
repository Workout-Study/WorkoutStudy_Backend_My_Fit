package com.fitmate.myfit.common

class GlobalStatus {

    companion object {
        const val SPRING_PROFILES_ACTIVE = "spring.profiles.active"
        const val SPRING_PROFILES_ACTIVE_DEFAULT = "local"

        const val PERSISTENCE_NOT_DELETED: Boolean = false
        const val PERSISTENCE_DELETED: Boolean = true

        const val VOTE_TARGET_CATEGORY_CERTIFICATION = 1
        const val VOTE_AGREE = true
        const val VOTE_DISAGREE = false

        const val KAFKA_GROUP_ID = "fit-group-service"
        const val KAFKA_TOPIC_FIT_GROUP = "fit-group"
        const val KAFKA_TOPIC_FIT_MATE = "fit-mate"
        const val KAFKA_TOPIC_FIT_CERTIFICATION = "fit-certification"
        const val KAFKA_TOPIC_FIT_CERTIFICATION_RESULT = "fit-certification-result"
        const val KAFKA_TOPIC_FIT_PENALTY = "fit-penalty"

        const val PENALTY_PAID = true
        const val PENALTY_NOT_PAID = false

        const val PENALTY_NEED_PAY = true
        const val PENALTY_NO_NEED_PAY = false
    }
}