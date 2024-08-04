package com.musinsa.codymaker.brand.domain.repository

import com.musinsa.codymaker.brand.domain.infra.BrandJpaInfra
import com.musinsa.codymaker.brand.domain.model.Brand
import org.springframework.stereotype.Repository

@Repository
class BrandRepository(private val jpaInfra: BrandJpaInfra) {

    fun save(brand: Brand): Brand = jpaInfra.save(brand)
}