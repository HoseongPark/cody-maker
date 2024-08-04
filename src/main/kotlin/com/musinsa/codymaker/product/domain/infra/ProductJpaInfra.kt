package com.musinsa.codymaker.product.domain.infra

import com.musinsa.codymaker.product.domain.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductJpaInfra : JpaRepository<Product, Long>