package com.musinsa.codymaker.stat.service.model

import com.musinsa.codymaker.product.domain.model.Category

data class CategoryProductStatDto(
    val category: Category,
    val brandName: String? = null,
    val minPrice: StatData? = null,
    val maxPrice: StatData? = null,
) {

    data class StatData(
        val brandName: String,
        val category: Category,
        val price: Int
    )
}
