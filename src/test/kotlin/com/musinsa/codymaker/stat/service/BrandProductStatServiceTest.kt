package com.musinsa.codymaker.stat.service

import com.musinsa.codymaker.brand.domain.infra.BrandJpaInfra
import com.musinsa.codymaker.brand.domain.model.Brand
import com.musinsa.codymaker.brand.domain.repository.BrandRepository
import com.musinsa.codymaker.common.config.QueryDslConfig
import com.musinsa.codymaker.product.domain.infra.ProductJpaInfra
import com.musinsa.codymaker.product.domain.model.Category
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
class BrandProductStatServiceTest(
    private val productJpaInfra: ProductJpaInfra,
    private val brandJpaInfra: BrandJpaInfra,
    private val queryFactory: JPAQueryFactory,
) : BehaviorSpec({

    val productRepo = ProductRepository(productJpaInfra, queryFactory)
    val brandRepo = BrandRepository(brandJpaInfra)
    val priceUseCase = PriceUseCase()

    val brandMinPriceProductStatService = BrandMinPriceProductStatService(productRepo, brandRepo, priceUseCase)

    Given("상품들이 저장되어 있고") {
        val nike = brandRepo.save(Brand("Nike"))

        productRepo.save(Product(nike.id!!, Category.TOP, 120_000))
        productRepo.save(Product(nike.id!!, Category.TOP, 30_000))

        productRepo.save(Product(nike.id!!, Category.BOTTOM, 230_000))
        productRepo.save(Product(nike.id!!, Category.BOTTOM, 555_000))

        productRepo.save(Product(nike.id!!, Category.OUTER, 430_000))
        productRepo.save(Product(nike.id!!, Category.OUTER, 11_000))

        productRepo.save(Product(nike.id!!, Category.HAT, 10_300))
        productRepo.save(Product(nike.id!!, Category.HAT, 4456_300))

        productRepo.save(Product(nike.id!!, Category.SHOES, 45_000))
        productRepo.save(Product(nike.id!!, Category.SHOES, 123_000))

        productRepo.save(Product(nike.id!!, Category.ACCESSORY, 23_000))
        productRepo.save(Product(nike.id!!, Category.ACCESSORY, 11_000))

        productRepo.save(Product(nike.id!!, Category.BAG, 32_000))
        productRepo.save(Product(nike.id!!, Category.BAG, 1_000))

        productRepo.save(Product(nike.id!!, Category.SOCKS, 12_000))
        productRepo.save(Product(nike.id!!, Category.SOCKS, 2345_000))

        val brandName = "Nike"

        When("브랜드에 따른 상품 통계를 조회하면") {
            val productStat = brandMinPriceProductStatService.getStat(brandName)

            Then("브랜드에 따른 최저 상품 통계를 알 수 있다.") {
                productStat.brandName shouldBe brandName
                productStat.minPrice.size shouldBe 8
                productStat.totalPrice shouldBe 350_300
            }
        }
    }

})
