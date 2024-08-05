package com.musinsa.codymaker.product.domain.repository

import com.musinsa.codymaker.common.exception.NotFoundException
import com.musinsa.codymaker.product.domain.infra.ProductJpaInfra
import com.musinsa.codymaker.product.domain.model.Category.TOP
import com.musinsa.codymaker.product.domain.model.Product
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest(private val productJpaInfra: ProductJpaInfra) : BehaviorSpec({

    val productRepository = ProductRepository(productJpaInfra)

    Given("상품이 주어지고") {
        val product = Product(1L, TOP, 100_000)

        When("상품이 저장이 되면") {
            val savedProduct = productRepository.save(product)

            Then("상품의 정보를 알 수 있다.") {
                savedProduct.id shouldBe 1L
                savedProduct.brandId shouldBe 1L
                savedProduct.category shouldBe TOP
                savedProduct.price shouldBe 100_000
            }
        }
    }

    Given("상품이 저장되어 있고") {
        val product = Product(1L, TOP, 100_000)
        val savedProduct = productRepository.save(product)

        When("존재하는 상품의 정보를 조회 하면") {
            val findProduct = productRepository.getById(savedProduct.id!!)

            Then("상품의 정보를 알 수 있다.") {
                findProduct.id shouldBe savedProduct.id
                findProduct.brandId shouldBe 1L
                findProduct.category shouldBe TOP
                findProduct.price shouldBe 100_000
            }
        }

        When("존재하지 않는 상품의 정보를 조회 하면") {
            val exception = shouldThrow<NotFoundException> {
                productRepository.getById(100000L)
            }

            Then("예외가 발생한다.") {
                exception.shouldBeInstanceOf<NotFoundException>()
            }
        }

    }

})
