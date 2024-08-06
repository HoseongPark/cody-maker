package com.musinsa.codymaker.stat.service

import com.musinsa.codymaker.brand.domain.repository.BrandRepository
import com.musinsa.codymaker.product.domain.model.Category
import com.musinsa.codymaker.product.domain.model.Product
import com.musinsa.codymaker.product.domain.repository.ProductRepository
import com.musinsa.codymaker.product.domain.usecase.PriceUseCase
import com.musinsa.codymaker.stat.service.model.BrandMinPriceProductStatDto
import org.springframework.stereotype.Service

@Service
class BrandMinPriceProductStatService(
    private val productRepo: ProductRepository,
    private val brandRepo: BrandRepository,
    private val priceUseCase: PriceUseCase
) {

    fun getStat(brandName: String): BrandMinPriceProductStatDto {
        val products = Category.entries.map { getMinPriceProducts(brandName, it) }
        if (products.isEmpty()) return BrandMinPriceProductStatDto(brandName = brandName)

        val totalPrice = priceUseCase.calculateTotalPrice(products)

        val minPriceStatData = products.map {
            BrandMinPriceProductStatDto.StatData(
                brandName = brandName,
                category = it.category,
                price = it.price
            )
        }

        val stat = BrandMinPriceProductStatDto(
            brandName = brandName,
            minPrice = minPriceStatData,
            totalPrice = totalPrice
        )

        return stat
    }

    private fun getMinPriceProducts(brandName: String, category: Category): Product {
        val brand = brandRepo.getByName(brandName)
        val minPrice = productRepo.findMinPrice(brandId = brand.id, category = category)
        val products = productRepo.findByCategoryAndPrice(category, minPrice)

        val product = priceUseCase.minPriceProduct(products)

        return product
    }

}