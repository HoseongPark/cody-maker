package com.musinsa.codymaker.product.service

import com.musinsa.codymaker.brand.domain.infra.BrandJpaInfra
import com.musinsa.codymaker.brand.domain.model.Brand
import com.musinsa.codymaker.brand.domain.repository.BrandRepository
import com.musinsa.codymaker.common.config.QueryDslConfig
import com.musinsa.codymaker.common.exception.NotFoundException
import com.musinsa.codymaker.product.controller.model.ProductSaveReq
import com.musinsa.codymaker.product.controller.model.ProductUpdateReq
import com.musinsa.codymaker.product.domain.infra.ProductJpaInfra
import com.musinsa.codymaker.product.domain.model.Category
import com.musinsa.codymaker.product.domain.model.Product
import com.musinsa.codymaker.product.domain.repository.ProductRepository
import com.querydsl.jpa.impl.JPAQueryFactory
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(QueryDslConfig::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductServiceTest(
    private val productJpaInfra: ProductJpaInfra,
    private val queryFactory: JPAQueryFactory,
    private val brandJpaInfra: BrandJpaInfra
) : BehaviorSpec({

    isolationMode = IsolationMode.InstancePerLeaf

    val productRepo = ProductRepository(productJpaInfra, queryFactory)
    val brandRepo = BrandRepository(brandJpaInfra)

    val productSvc = ProductService(productRepo, brandRepo)

    Given("브랜드가 저장되어 있고, 상품 정보가 주어지면") {
        val brand = Brand("Nike")
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

            Then("NotFoundException 예외가 발생한다") {
                exception.shouldBeInstanceOf<NotFoundException>()
            }
        }
    }

    Given("상품이 저장되어 있고") {
        val product = Product(brandId = 1L, Category.HAT, 10000)
        val savedProduct = productRepo.save(product)

        When("수정하면") {
            val productUpdateReq = ProductUpdateReq("BOTTOM", 20000)
            val updateProductId = productSvc.updateProduct(savedProduct.id!!, productUpdateReq)

            Then("상품 ID를 반환 한다.") {
                savedProduct.id shouldBe updateProductId
            }
        }

        When("수정 하려는 상품이 존재하지 않으면") {
            val productUpdateReq = ProductUpdateReq("BOTTOM", 20000)

            val exception = shouldThrow<NotFoundException> {
                productSvc.updateProduct(30L, productUpdateReq)
            }

            Then("NotFoundException 예외가 발생한다") {
                exception.shouldBeInstanceOf<NotFoundException>()
            }
        }

        When("삭제하면") {
            productSvc.deleteProduct(savedProduct.id!!)

            Then("상품의 삭제 상태가 true로 변경된다.") {
                val getProduct = productRepo.getById(savedProduct.id!!)
                getProduct.deleted shouldBe true
            }
        }

        When("삭제하려는 상품이 존재하지 않으면") {
            val exception = shouldThrow<NotFoundException> {
                productSvc.deleteProduct(55L)
            }

            Then("NotFoundException 예외가 발생한다") {
                exception.shouldBeInstanceOf<NotFoundException>()
            }
        }
    }

})
