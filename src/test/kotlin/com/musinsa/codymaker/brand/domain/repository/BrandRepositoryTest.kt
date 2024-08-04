package com.musinsa.codymaker.brand.domain.repository

import com.musinsa.codymaker.brand.domain.infra.BrandJpaInfra
import com.musinsa.codymaker.brand.domain.model.Brand
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestPropertySource

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BrandRepositoryTest (private val jpaInfra: BrandJpaInfra) : BehaviorSpec({

    val brandRepository = BrandRepository(jpaInfra)

    Given("브랜드 저장") {
        When("브랜드 도메인이 저장이 되면") {
            val brand = Brand("Nike", true)
            val savedBrand = brandRepository.save(brand)

            Then("브랜드의 정보를 알 수 있다.") {
                savedBrand.id shouldBe 1L
                savedBrand.name shouldBe "Nike"
                savedBrand.delete shouldBe true
            }
        }
    }
})
