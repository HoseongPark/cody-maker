package com.musinsa.codymaker.brand.domain.model

import com.musinsa.codymaker.common.model.BaseEntity
import jakarta.persistence.Entity

@Entity
class Brand(name: String, deleted: Boolean) : BaseEntity() {

    // 브랜드 이름은 중복되지 않은 유일한 이름이라고 가정한다.
    var name: String = name

    var deleted: Boolean = deleted
        private set

    fun update(name: String?) {
        name?.let { this.name = it }
    }

    fun delete() {
        this.deleted = true
    }

}