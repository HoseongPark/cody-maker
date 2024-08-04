package com.musinsa.codymaker.product.domain.repository

import com.musinsa.codymaker.product.domain.infra.ProductJpaInfra
import com.musinsa.codymaker.product.domain.model.Category.TOP
import com.musinsa.codymaker.product.domain.model.Product
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest(private val productJpaInfra: ProductJpaInfra) : BehaviorSpec({

    extensions(SpringTestExtension(SpringTestLifecycleMode.Root))
    isolationMode = IsolationMode.InstancePerLeaf

    val productRepository = ProductRepository(productJpaInfra)

    Given("상품 저장 요청이 주어지고") {
        When("상품 도메인이 저장이 되면") {
            val product = Product(1L, TOP, 100_000)

            val savedProduct = productRepository.save(product)

            Then("상품의 정보를 알 수 있다.") {
                savedProduct.id shouldBe 1L
                savedProduct.brandId shouldBe 1L
                savedProduct.category shouldBe TOP
                savedProduct.price shouldBe 100_000
            }
        }
    }

})
