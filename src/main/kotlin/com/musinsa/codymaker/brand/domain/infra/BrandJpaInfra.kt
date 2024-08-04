package com.musinsa.codymaker.brand.domain.infra

import com.musinsa.codymaker.brand.domain.model.Brand
import org.springframework.data.jpa.repository.JpaRepository

interface BrandJpaInfra : JpaRepository<Brand, Long>