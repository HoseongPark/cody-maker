package com.musinsa.codymaker.brand.domain.repository

import com.musinsa.codymaker.brand.domain.infra.BrandJpaInfra
import com.musinsa.codymaker.brand.domain.model.Brand
import com.musinsa.codymaker.common.exception.NotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BrandRepositoryTest (private val jpaInfra: BrandJpaInfra) : BehaviorSpec({

    val brandRepository = BrandRepository(jpaInfra)

    Given("브랜드 정보가 주어지고") {
        val brand = Brand("Nike")

        When("저장이 되면") {
            val savedBrand = brandRepository.save(brand)

            Then("브랜드의 정보를 알 수 있다") {
                savedBrand.name shouldBe "Nike"
                savedBrand.deleted shouldBe false
            }
        }
    }

    Given("브랜드가 저장되어 있고") {
        val brand = Brand("Adidas")
        val savedBrand = brandRepository.save(brand)

        When("조회 하면") {
            val foundBrand = brandRepository.getById(savedBrand.id!!)

            Then("브랜드의 정보를 알 수 있다.") {
                foundBrand.name shouldBe "Adidas"
                foundBrand.deleted shouldBe false
            }
        }

        When("존재하지 않는 브랜드를 조회 하면") {
            val exception = shouldThrow<NotFoundException> {
                brandRepository.getById(10000L)
            }

            Then("예외가 발생한다.") {
                exception.shouldBeInstanceOf<NotFoundException>()
            }
        }

    }
})
