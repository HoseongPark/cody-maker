package com.musinsa.codymaker.stat.service

import com.musinsa.codymaker.brand.domain.repository.BrandRepository
import com.musinsa.codymaker.product.domain.model.Category
import com.musinsa.codymaker.product.domain.repository.ProductRepository
import com.musinsa.codymaker.product.domain.usecase.PriceUseCase
import com.musinsa.codymaker.stat.service.model.CategoryMinPriceProductStatDto
import org.springframework.stereotype.Service

@Service
class CategoryMinPriceProductStatService(
    private val productRepo: ProductRepository,
    private val brandRepo: BrandRepository,
    private val priceUseCase: PriceUseCase
) {

    fun getStat(): CategoryMinPriceProductStatDto {
        // 카테고리별 최소 가격 상품 조회
        val products = Category.entries.map {
            val minPrice = productRepo.findMinPrice(it)
            val products = productRepo.findByCategoryAndPrice(it, minPrice)
            val product = priceUseCase.minPriceProduct(products)
            product
        }

        // 상품의 총 금액 계산
        val totalPrice = priceUseCase.calculateTotalPrice(products)

        // 카테고리별 최소 가격 상품 통계 데이터 생성
        val stats = products.map { product ->
            val brand = brandRepo.getById(product.brandId)

            CategoryMinPriceProductStatDto.Stat(
                brandName = brand.name,
                category = product.category,
                price = product.price
            )
        }

        val categoryMinPriceProductStatDto = CategoryMinPriceProductStatDto(
            minPrice = stats,
            totalPrice = totalPrice
        )

        return categoryMinPriceProductStatDto
    }

}