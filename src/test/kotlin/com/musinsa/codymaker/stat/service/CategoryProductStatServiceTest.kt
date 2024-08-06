package com.musinsa.codymaker.stat.service

import com.musinsa.codymaker.brand.domain.infra.BrandJpaInfra
import com.musinsa.codymaker.brand.domain.model.Brand
import com.musinsa.codymaker.brand.domain.repository.BrandRepository
import com.musinsa.codymaker.common.config.QueryDslConfig
import com.musinsa.codymaker.product.domain.infra.ProductJpaInfra
import com.musinsa.codymaker.product.domain.model.Category.TOP
import com.musinsa.codymaker.product.domain.model.Product
import com.musinsa.codymaker.product.domain.repository.ProductRepository
import com.musinsa.codymaker.product.domain.usecase.PriceUseCase
import com.querydsl.jpa.impl.JPAQueryFactory
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(QueryDslConfig::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryProductStatServiceTest(
    private val productJpaInfra: ProductJpaInfra,
    private val brandJpaInfra: BrandJpaInfra,
    private val queryFactory: JPAQueryFactory,
) : BehaviorSpec({

    val productRepo = ProductRepository(productJpaInfra, queryFactory)
    val brandRepo = BrandRepository(brandJpaInfra)
    val priceUseCase = PriceUseCase()

    val categoryProductStatService = CategoryProductStatService(productRepo, brandRepo, priceUseCase)

    Given("상품들이 저장되어 있고") {
        val nike = brandRepo.save(Brand("Nike"))
        val adidas = brandRepo.save(Brand("Adidas"))

        val product1 = Product(nike.id!!, TOP, 100_000)
        val product2 = Product(adidas.id!!, TOP, 100_000)
        val product3 = Product(adidas.id!!, TOP, 300_000)
        val product4 = Product(nike.id!!, TOP, 500_000)
        val product5 = Product(adidas.id!!, TOP, 500_000)

        productRepo.save(product1)
        productRepo.save(product2)
        productRepo.save(product3)
        productRepo.save(product4)
        productRepo.save(product5)

        val category = TOP

        When("카테고리에 따른 상품 통계를 조회하면") {
            val productStat = categoryProductStatService.getStat(category)

            Then("카테고리에 따른 상품 통계를 알 수 있다.") {
                productStat.category shouldBe category
                productStat.minPrice?.price shouldBe 100_000
                productStat.minPrice?.brandName shouldBe "Nike"
                productStat.maxPrice?.price shouldBe 500_000
                productStat.maxPrice?.brandName shouldBe "Nike"
            }
        }
    }
})
