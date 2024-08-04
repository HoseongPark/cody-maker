package com.musinsa.codymaker.brand.domain.model

import com.musinsa.codymaker.common.model.BaseEntity
import jakarta.persistence.Entity

@Entity
class Brand(name: String, deleted: Boolean) : BaseEntity() {
    var name: String = name
    var delete: Boolean = deleted
}