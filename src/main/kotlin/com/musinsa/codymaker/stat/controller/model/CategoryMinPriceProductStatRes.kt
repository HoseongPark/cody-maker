package com.musinsa.codymaker.stat.controller.model

import com.musinsa.codymaker.product.domain.model.Category
import com.musinsa.codymaker.stat.service.model.CategoryMinPriceProductStatDto

data class CategoryMinPriceProductStatRes(
    val stats: List<Stat>,
    val totalPrice: Int
) {

    data class Stat(
        val category: Category,
        val brandName: String,
        val price: Int
    )

    companion object {
        fun from(categoryMinPriceProductStatDto: CategoryMinPriceProductStatDto): CategoryMinPriceProductStatRes {
            val stats = categoryMinPriceProductStatDto.minPrice.map {
                Stat(
                    category = it.category,
                    brandName = it.brandName,
                    price = it.price
                )
            }

            return CategoryMinPriceProductStatRes(
                stats = stats,
                totalPrice = categoryMinPriceProductStatDto.totalPrice
            )
        }
    }
}
