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
class CategoryMinPriceProductStatServiceTest(
    private val productJpaInfra: ProductJpaInfra,
    private val brandJpaInfra: BrandJpaInfra,
    private val queryFactory: JPAQueryFactory,
) : BehaviorSpec({

    val productRepo = ProductRepository(productJpaInfra, queryFactory)
    val brandRepo = BrandRepository(brandJpaInfra)
    val priceUseCase = PriceUseCase()

    val categoryMinPriceProductStatService = CategoryMinPriceProductStatService(productRepo, brandRepo, priceUseCase)

    Given("상품들이 저장되어 있고") {
        val nike = brandRepo.save(Brand("Nike"))
        val adidas = brandRepo.save(Brand("Adidas"))
        val puma = brandRepo.save(Brand("Puma"))
        val balenciaga = brandRepo.save(Brand("Balenciaga"))

        productRepo.save(Product(nike.id!!, Category.TOP, 10_040))
        productRepo.save(Product(adidas.id!!, Category.TOP, 20_300))
        productRepo.save(Product(puma.id!!, Category.TOP, 1_200))
        productRepo.save(Product(balenciaga.id!!, Category.TOP, 1_230_000))

        productRepo.save(Product(nike.id!!, Category.BOTTOM, 230_000))
        productRepo.save(Product(adidas.id!!, Category.BOTTOM, 155_000))
        productRepo.save(Product(puma.id!!, Category.BOTTOM, 220_000))
        productRepo.save(Product(balenciaga.id!!, Category.BOTTOM, 5_000))

        productRepo.save(Product(nike.id!!, Category.OUTER, 130_000))
        productRepo.save(Product(adidas.id!!, Category.OUTER, 11_000))
        productRepo.save(Product(puma.id!!, Category.OUTER, 430_000))
        productRepo.save(Product(balenciaga.id!!, Category.OUTER, 11_000_000))

        productRepo.save(Product(nike.id!!, Category.HAT, 11_300))
        productRepo.save(Product(adidas.id!!, Category.HAT, 2_300))
        productRepo.save(Product(puma.id!!, Category.HAT, 34_300))
        productRepo.save(Product(balenciaga.id!!, Category.HAT, 4456_300))

        productRepo.save(Product(nike.id!!, Category.SHOES, 32_000))
        productRepo.save(Product(adidas.id!!, Category.SHOES, 11_000))
        productRepo.save(Product(puma.id!!, Category.SHOES, 2_000))
        productRepo.save(Product(balenciaga.id!!, Category.SHOES, 54_000))

        productRepo.save(Product(nike.id!!, Category.ACCESSORY, 234_000))
        productRepo.save(Product(adidas.id!!, Category.ACCESSORY, 65_000))
        productRepo.save(Product(puma.id!!, Category.ACCESSORY, 54_000))
        productRepo.save(Product(balenciaga.id!!, Category.ACCESSORY, 1_000))

        productRepo.save(Product(nike.id!!, Category.BAG, 12_000))
        productRepo.save(Product(adidas.id!!, Category.BAG, 6_000))
        productRepo.save(Product(puma.id!!, Category.BAG, 3_000))
        productRepo.save(Product(balenciaga.id!!, Category.BAG, 1_235_000))

        productRepo.save(Product(nike.id!!, Category.SOCKS, 11_000))
        productRepo.save(Product(adidas.id!!, Category.SOCKS, 2345_000))
        productRepo.save(Product(puma.id!!, Category.SOCKS, 9_000))
        productRepo.save(Product(balenciaga.id!!, Category.SOCKS, 2_345_000))

        When("카테고리별 최저 가격 상품 통계를 조회하면") {
            val productStat = categoryMinPriceProductStatService.getStat()

            Then("카테고리별 최저 가격 상품 통계를 알 수 있다.") {
                productStat.minPrice.size shouldBe 8
                productStat.totalPrice shouldBe 34_500
            }
        }
    }
})
