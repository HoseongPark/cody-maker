package com.musinsa.codymaker.product.domain.model

import com.musinsa.codymaker.product.domain.infra.ProductJpaInfra
import com.musinsa.codymaker.product.domain.repository.ProductRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductTest(private val productJpaInfra: ProductJpaInfra) : BehaviorSpec({

    isolationMode = IsolationMode.InstancePerLeaf

    val productRepository = ProductRepository(productJpaInfra)

    Given("상품이 저장 되어 있고") {
        val product = Product(1, Category.TOP, 10000)
        val savedProduct = productRepository.save(product)

        When("상품 카테고리를 수정 하면") {
            savedProduct.update(category = Category.BOTTOM)

            Then("카테고리가 수정 된다") {
                savedProduct.category shouldBe Category.BOTTOM
                savedProduct.price shouldBe 10000
            }
        }

        When("상품 가격을 수정 하면") {
            savedProduct.update(price = 20000)

            Then("가격이 수정 된다") {
                savedProduct.price shouldBe 20000
            }
        }

        When("카테고리와 가격을 수정 하면") {
            savedProduct.update(Category.HAT, 30000)

            Then("카테고리와 가격이 수정 된다.") {
                savedProduct.category shouldBe Category.HAT
                savedProduct.price shouldBe 30000
            }
        }

        When("수정 값이 없다면") {
            savedProduct.update(null, null)

            Then("수정되지 않는다.") {
                savedProduct.category shouldBe Category.TOP
                savedProduct.price shouldBe 10000
            }
        }

        When("상품을 삭제 하면") {
            savedProduct.delete()

            Then("삭제 상태가 true로 변경 된다.") {
                savedProduct.deleted shouldBe true
            }
        }
    }
})
