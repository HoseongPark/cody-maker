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
        When("브랜드 수정 메소드가 호출 되면") {
            val brand = Brand("Nike", true)
            val savedBrand = brandRepository.save(brand)

            savedBrand.updateBrand("Adidas", false)

            Then("브랜드 정보가 수정 된다.") {
                savedBrand.name shouldBe "Adidas"
                savedBrand.deleted shouldBe false
            }
        }
    }

})
