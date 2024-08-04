package com.musinsa.codymaker.brand.domain.repository

import com.musinsa.codymaker.brand.domain.infra.BrandJpaInfra
import com.musinsa.codymaker.brand.domain.model.Brand
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BrandRepositoryTest (private val jpaInfra: BrandJpaInfra) : BehaviorSpec({

    extensions(SpringTestExtension(SpringTestLifecycleMode.Root))
    isolationMode = IsolationMode.InstancePerLeaf

    val brandRepository = BrandRepository(jpaInfra)

    Given("브랜드 저장") {
        When("브랜드 도메인이 저장이 되면") {
            val brand = Brand("Nike", true)
            val savedBrand = brandRepository.save(brand)

            Then("브랜드의 정보를 알 수 있다.") {
                savedBrand.id shouldBe 1L
                savedBrand.name shouldBe "Nike"
                savedBrand.deleted shouldBe true
            }
        }
    }

    Given("브랜드 조회") {
        When("존재하는 브랜드의 정보를 조회 하면") {
            val brand = Brand("Nike", true)
            brandRepository.save(brand)

            val foundBrand = brandRepository.getById(1L)

            Then("브랜드의 정보를 알 수 있다.") {
                foundBrand.id shouldBe 1L
                foundBrand.name shouldBe "Nike"
                foundBrand.deleted shouldBe true
            }
        }

        When("존재하지 않는 브랜드의 정보를 조회 하면") {
            val exception = shouldThrow<RuntimeException> {
                brandRepository.getById(1L)
            }

            Then("예외가 발생한다.") {
                exception.shouldBeInstanceOf<RuntimeException>()
            }
        }

    }
})
