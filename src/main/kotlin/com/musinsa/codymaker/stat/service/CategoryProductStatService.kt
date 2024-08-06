package com.musinsa.codymaker.stat.service

import com.musinsa.codymaker.brand.domain.repository.BrandRepository
import com.musinsa.codymaker.product.domain.model.Category
import com.musinsa.codymaker.product.domain.repository.ProductRepository
import com.musinsa.codymaker.product.domain.usecase.PriceUseCase
import com.musinsa.codymaker.stat.service.model.CategoryProductStatDto
import org.springframework.stereotype.Service

/**
 * 카테고리에 따른 상품 통계를 제공하는 서비스
 */
@Service
class CategoryProductStatService(
    private val productRepo: ProductRepository,
    private val brandRepo: BrandRepository,
    private val priceUseCase: PriceUseCase
) {

    fun getStat(category: Category): CategoryProductStatDto {
        val minProduct = getMinPriceStatData(category)
        val maxProduct = getMaxPriceStatData(category)

        val categoryProductStatDto = CategoryProductStatDto(
            category = category,
            minPrice = minProduct,
            maxPrice = maxProduct
        )

        return categoryProductStatDto
    }

    private fun getMaxPriceStatData(category: Category): CategoryProductStatDto.StatData? {
        val maxPrice = productRepo.findMaxPrice(category)
        val products = productRepo.findByCategoryAndPrice(category, maxPrice)

        if (products.isEmpty()) return null

        val product = priceUseCase.maxPriceProduct(products)
        val brand = brandRepo.getById(product.brandId)

        val minStatData = CategoryProductStatDto.StatData(
            brandName = brand.name,
            category = product.category,
            price = product.price
        )

        return minStatData
    }

    private fun getMinPriceStatData(category: Category): CategoryProductStatDto.StatData? {
        val minPrice = productRepo.findMinPrice(category)
        val products = productRepo.findByCategoryAndPrice(category, minPrice)

        if (products.isEmpty()) return null

        val product = priceUseCase.minPriceProduct(products)
        val brand = brandRepo.getById(product.brandId)

        val maxStatData = CategoryProductStatDto.StatData(
            brandName = brand.name,
            category = product.category,
            price = product.price
        )

        return maxStatData
    }
}