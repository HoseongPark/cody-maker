package com.musinsa.codymaker.product.domain.usecase

import com.musinsa.codymaker.product.domain.model.Product
import org.springframework.stereotype.Service

@Service
class PriceUseCase {

    fun calculateTotalPrice(products: List<Product>): Int {
        return products.sumOf { it.price }
    }

    // 가격이 같다면 브랜드 ID가 낮은 상품을 반환으로 가정 한다.
    // 브랜드 ID가 같다면 가장 첫번째 상품을 반환으로 가정 한다.
    fun minPriceProduct(products: List<Product>): Product {
        if (products.isEmpty()) throw IllegalArgumentException("products is empty")
        val sortedBy = products.sortedBy { it.brandId }

        return sortedBy.first()
    }

    // 가격이 같다면 브랜드 ID가 낮은 상품을 반환으로 가정 한다.
    // 브랜드 ID가 같다면 가장 첫번째 상품을 반환으로 가정 한다.
    fun maxPriceProduct(products: List<Product>): Product {
        if (products.isEmpty()) throw IllegalArgumentException("products is empty")
        val sortedBy = products.sortedBy { it.brandId }

        return sortedBy.first()
    }
}