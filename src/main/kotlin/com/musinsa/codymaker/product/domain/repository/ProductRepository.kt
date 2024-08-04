package com.musinsa.codymaker.product.domain.repository

import com.musinsa.codymaker.product.domain.infra.ProductJpaInfra
import com.musinsa.codymaker.product.domain.model.Product
import org.springframework.stereotype.Repository

@Repository
class ProductRepository(private val productJpaInfra: ProductJpaInfra) {

    fun save(product: Product) = productJpaInfra.save(product)

}