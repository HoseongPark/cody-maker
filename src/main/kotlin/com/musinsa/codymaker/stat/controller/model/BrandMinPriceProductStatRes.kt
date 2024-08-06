package com.musinsa.codymaker.stat.controller.model

import com.musinsa.codymaker.product.domain.model.Category
import com.musinsa.codymaker.stat.service.model.BrandMinPriceProductStatDto

data class BrandMinPriceProductStatRes(
    val minPrice: Stat
) {
    data class Stat(
        val brandName: String,
        val category: List<CategoryStat>,
        val totalPrice: Int?
    ) {
        data class CategoryStat(
            val category: Category,
            val price: Int
        )
    }

    companion object {
        fun from(brandMinPriceProductStatDto: BrandMinPriceProductStatDto): BrandMinPriceProductStatRes {
            val categoryStat = brandMinPriceProductStatDto.minPrice.map {
                Stat.CategoryStat(
                    category = it.category,
                    price = it.price
                )
            }

            val stat = Stat(
                brandName = brandMinPriceProductStatDto.brandName,
                category = categoryStat,
                totalPrice = brandMinPriceProductStatDto.totalPrice
            )

            return BrandMinPriceProductStatRes(stat)
        }
    }
}
