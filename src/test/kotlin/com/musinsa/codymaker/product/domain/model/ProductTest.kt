package com.musinsa.codymaker.product.domain.model

import com.musinsa.codymaker.product.domain.infra.ProductJpaInfra
import com.musinsa.codymaker.product.domain.repository.ProductRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductTest(private val productJpaInfra: ProductJpaInfra) : BehaviorSpec({

    extensions(SpringTestExtension(SpringTestLifecycleMode.Root))

    isolationMode = IsolationMode.InstancePerLeaf

    val productRepository = ProductRepository(productJpaInfra)


    Given("상품 수정") {
        val product = Product(1, Category.TOP, 10000)
        val savedProduct = productRepository.save(product)

        When("Category를 수정 하면") {
            savedProduct.update(category = Category.BOTTOM)

            Then("상품의 Category가 수정 된다.") {
                savedProduct.category shouldBe Category.BOTTOM
                savedProduct.price shouldBe 10000
            }
        }

        When("Price를 수정 하면") {
            savedProduct.update(price = 20000)

            Then("상품의 Price가 수정 된다.") {
                savedProduct.category shouldBe Category.TOP
                savedProduct.price shouldBe 20000
            }
        }

        When("Category와 Price를 수정 하면") {
            savedProduct.update(Category.BOTTOM, 20000)

            Then("상품의 Category와 Price가 수정 된다.") {
                savedProduct.category shouldBe Category.BOTTOM
                savedProduct.price shouldBe 20000
            }
        }

        When("수정 값이 Null이면") {
            savedProduct.update(null, null)

            Then("상품의 값이 수정되지 않는다.") {
                savedProduct.category shouldBe Category.TOP
                savedProduct.price shouldBe 10000
            }
        }
    }
})
