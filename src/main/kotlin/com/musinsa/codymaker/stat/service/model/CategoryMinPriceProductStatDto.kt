package com.musinsa.codymaker.stat.service.model

import com.musinsa.codymaker.product.domain.model.Category

data class CategoryMinPriceProductStatDto(
    val minPrice: List<Stat>,
    val totalPrice: Int
) {
    data class Stat(
        val category: Category,
        val brandName: String,
        val price: Int
    )
}
