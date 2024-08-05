package com.musinsa.codymaker.brand.domain.model

import com.musinsa.codymaker.brand.domain.infra.BrandJpaInfra
import com.musinsa.codymaker.brand.domain.repository.BrandRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BrandTest(private val jpaInfra: BrandJpaInfra) : BehaviorSpec({

    isolationMode = IsolationMode.InstancePerLeaf

    val brandRepository = BrandRepository(jpaInfra)

    Given("브랜드가 저장되어 있고") {
        val brand = Brand("Nike", false)
        val savedBrand = brandRepository.save(brand)

        When("이름을 수정 하면") {
            savedBrand.update("Adidas")

            Then("이름이 수정 된다") {
                savedBrand.name shouldBe "Adidas"
                savedBrand.deleted shouldBe false
            }
        }

        When("수정값이 없다면") {
            savedBrand.update(null)

            Then("수정되지 않는다") {
                savedBrand.name shouldBe "Nike"
                savedBrand.deleted shouldBe false
            }
        }

        When("삭제 하면") {
            savedBrand.delete()

            Then("삭제 여부가 true로 변경 된다.") {
                savedBrand.deleted shouldBe true
            }
        }
    }

})
