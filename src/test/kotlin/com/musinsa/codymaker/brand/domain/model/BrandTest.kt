package com.musinsa.codymaker.brand.domain.model

import com.musinsa.codymaker.brand.domain.infra.BrandJpaInfra
import com.musinsa.codymaker.brand.domain.repository.BrandRepository
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BrandTest(private val jpaInfra: BrandJpaInfra) : BehaviorSpec({

    extensions(SpringTestExtension(SpringTestLifecycleMode.Root))

    isolationMode = IsolationMode.InstancePerLeaf

    val brandRepository = BrandRepository(jpaInfra)

    Given("브랜드 수정") {
        val brand = Brand("Nike", true)
        val savedBrand = brandRepository.save(brand)

        When("Name을 수정 하면") {
            savedBrand.update("Adidas")

            Then("브랜드 Name이 수정 된다.") {
                savedBrand.name shouldBe "Adidas"
                savedBrand.deleted shouldBe true
            }
        }

        When("Name이 Null이면") {
            savedBrand.update(null)

            Then("브랜드 Name이 수정되지 않는다.") {
                savedBrand.name shouldBe "Nike"
                savedBrand.deleted shouldBe true
            }
        }
    }

    Given("브랜드 삭제") {
        When("브랜드 삭제 메소드가 호출 되면") {
            val brand = Brand("Nike", false)
            val savedBrand = brandRepository.save(brand)

            savedBrand.delete()

            Then("브랜드 삭제 여부가 true로 변경 된다.") {
                savedBrand.deleted shouldBe true
            }
        }
    }

})
