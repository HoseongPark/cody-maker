package com.musinsa.codymaker.brand.domain.repository

import com.musinsa.codymaker.brand.domain.infra.BrandJpaInfra
import com.musinsa.codymaker.brand.domain.model.Brand
import com.musinsa.codymaker.common.exception.NotFoundException
import org.springframework.stereotype.Repository

@Repository
class BrandRepository(private val jpaInfra: BrandJpaInfra) {

    fun save(brand: Brand): Brand = jpaInfra.save(brand)

    fun getById(id: Long): Brand {
        val brand = jpaInfra.findById(id).orElseThrow { throw NotFoundException("Brand not found") }
        return brand
    }
}