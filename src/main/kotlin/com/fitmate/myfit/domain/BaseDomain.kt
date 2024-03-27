package com.fitmate.myfit.domain

import com.fitmate.myfit.adapter.out.persistence.entity.BaseEntity
import com.fitmate.myfit.common.GlobalStatus
import java.time.Instant

open class BaseDomain(
    var state: Boolean,
    var createdAt: Instant,
    var createUser: String,
    var updatedAt: Instant? = null,
    var updateUser: String? = null,
) {

    protected fun setMetaDataByEntity(entity: BaseEntity) {
        this.createUser = entity.createUser
        this.createdAt = entity.createdAt
        this.updateUser = entity.updateUser
        this.updatedAt = entity.updatedAt
        this.state = entity.state
    }

    fun delete() {
        this.state = GlobalStatus.PERSISTENCE_DELETED
        this.updatedAt = Instant.now()
        this.updateUser = this.createUser
    }

    val isDeleted: Boolean
        get() = state
}