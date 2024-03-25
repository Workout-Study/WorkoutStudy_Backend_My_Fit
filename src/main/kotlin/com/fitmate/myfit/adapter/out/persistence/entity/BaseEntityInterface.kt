package com.fitmate.myfit.adapter.out.persistence.entity

import com.fitmate.myfit.domain.BaseDomain

interface BaseEntityInterface {

    fun domainToEntity(domain: BaseDomain): BaseEntity
}