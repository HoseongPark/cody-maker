package com.musinsa.codymaker.product.service

import com.musinsa.codymaker.brand.domain.infra.BrandJpaInfra
import com.musinsa.codymaker.brand.domain.model.Brand
import com.musinsa.codymaker.brand.domain.repository.BrandRepository
import com.musinsa.codymaker.common.exception.NotFoundException
import com.musinsa.codymaker.product.controller.model.ProductSaveReq
import com.musinsa.codymaker.product.domain.infra.ProductJpaInfra
import com.musinsa.codymaker.product.domain.repository.ProductRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductServiceTest(
    private val productJpaInfra: ProductJpaInfra,
    private val brandJpaInfra: BrandJpaInfra
) : BehaviorSpec({

    isolationMode = IsolationMode.InstancePerLeaf

    val productRepo = ProductRepository(productJpaInfra)
    val brandRepo = BrandRepository(brandJpaInfra)

    val productSvc = ProductService(productRepo, brandRepo)

    Given("브랜드가 저장되어 있고, 상품 정보가 주어지면") {
        val brand = Brand("Nike", false)
        val savedBrand = brandRepo.save(brand)

        val saveReq = ProductSaveReq(brandId = savedBrand.id!!, category = "TOP", price = 100_000)

        When("상품이 등록이 되었을 때") {
            val savedProductId = productSvc.saveProduct(saveReq)

            Then("상품 ID를 반환 한다.") {
                savedProductId shouldNotBe null
            }
        }

        When("상품의 브랜드가 없을 때") {
            val productSaveReq = ProductSaveReq(brandId = 100L, category = "TOP", price = 100_000)

            val exception = shouldThrow<NotFoundException> {
                productSvc.saveProduct(productSaveReq)
            }

            Then("예외를 반환 한다.") {
                exception.shouldBeInstanceOf<NotFoundException>()
            }
        }
    }

})
