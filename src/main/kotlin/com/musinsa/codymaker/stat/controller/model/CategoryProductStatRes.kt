package com.musinsa.codymaker.stat.controller.model

import com.musinsa.codymaker.product.domain.model.Category
import com.musinsa.codymaker.stat.service.model.CategoryProductStatDto

data class CategoryProductStatRes(
    val category: Category,
    val minPrice: List<Stat>?,
    val maxPrice: List<Stat>?,
) {

    data class Stat(
        val brandName: String,
        val price: Int
    )

    companion object {
        // 카테고리별 최저 및 최고가는 1개의 상품만을 가지게됨, 과제의 Response 값은 List로 반환하도록 되어있어 List로 반환하도록 수정
        fun from(categoryProductStatDto: CategoryProductStatDto): CategoryProductStatRes {
            val minStat = categoryProductStatDto.minPrice?.let {
                listOf(Stat(brandName = it.brandName, price = it.price))
            }

            val maxStat = categoryProductStatDto.maxPrice?.let {
                listOf(Stat(brandName = it.brandName, price = it.price))
            }

            return CategoryProductStatRes(categoryProductStatDto.category, minStat, maxStat)
        }
    }

}
