package com.musinsa.codymaker.stat.service.model

import com.musinsa.codymaker.product.domain.model.Category

data class BrandMinPriceProductStatDto(
    val brandName: String,
    val minPrice: List<StatData> = emptyList(),
    val maxPrice: List<StatData> = emptyList(),
    val totalPrice: Int? = null
) {

    data class StatData(
        val brandName: String,
        val category: Category,
        val price: Int
    )
}
