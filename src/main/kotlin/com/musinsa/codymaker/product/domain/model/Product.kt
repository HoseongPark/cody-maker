package com.musinsa.codymaker.product.domain.model

import com.musinsa.codymaker.common.model.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated

@Entity
class Product(brandId: Long, category: Category, price: Int) : BaseEntity() {

    @Column(name = "brand_id")
    val brandId: Long = brandId

    @Enumerated(STRING)
    var category: Category = category
        private set

    var price: Int = price
        private set

    /**
     * 상품의 카테고리와, 가격을 수정 할 수 있다.
     */
    fun update(category: Category? = null, price: Int? = null) {
        category?.let { this.category = it }
        price?.let { this.price = it }
    }

}