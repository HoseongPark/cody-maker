package com.musinsa.codymaker.product.domain.repository

import com.musinsa.codymaker.common.config.QueryDslConfig
import com.musinsa.codymaker.common.exception.NotFoundException
import com.musinsa.codymaker.product.domain.infra.ProductJpaInfra
import com.musinsa.codymaker.product.domain.model.Category.TOP
import com.musinsa.codymaker.product.domain.model.Product
import com.querydsl.jpa.impl.JPAQueryFactory
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(QueryDslConfig::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest(
    private val productJpaInfra: ProductJpaInfra,
    private val queryFactory: JPAQueryFactory
) : BehaviorSpec({

    val productRepository = ProductRepository(productJpaInfra, queryFactory)

    Given("상품이 주어지고") {
        val product = Product(1L, TOP, 100_000)

        When("상품이 저장이 되면") {
            val savedProduct = productRepository.save(product)

            Then("상품의 정보를 알 수 있다.") {
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

    Given("여러개의 상품이 저장 되어 있고") {
        val product1 = Product(1L, TOP, 100_000)
        val product2 = Product(2L, TOP, 200_000)
        val product3 = Product(3L, TOP, 300_000)
        val product4 = Product(1L, TOP, 400_000)
        val product5 = Product(2L, TOP, 500_000)

        productRepository.save(product1)
        productRepository.save(product2)
        productRepository.save(product3)
        productRepository.save(product4)
        productRepository.save(product5)

        When("특정 카테고리의 최저 가격을 조회 하면") {
            val minPrice = productRepository.findMinPrice(TOP)

            then("최저 가격을 알 수 있다.") {
                minPrice shouldBe 100_000
            }
        }

        When("특정 카테고리의 최고 가격을 조회 하면") {
            val maxPrice = productRepository.findMaxPrice(TOP)

            then("최고 가격을 알 수 있다.") {
                maxPrice shouldBe 500_000
            }
        }

        When("카테고리와 가격으로 상품을 조회 하면") {
            val products = productRepository.findByCategoryAndPrice(TOP, 200_000)

            then("상품을 알 수 있다.") {
                products.size shouldBe 1
                products[0].brandId shouldBe 2L
                products[0].category shouldBe TOP
                products[0].price shouldBe 200_000
            }
        }
    }

})
