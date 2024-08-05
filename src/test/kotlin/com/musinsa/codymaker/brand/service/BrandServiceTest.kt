package com.musinsa.codymaker.brand.service

import com.musinsa.codymaker.brand.controller.model.BrandSaveReq
import com.musinsa.codymaker.brand.controller.model.BrandUpdateReq
import com.musinsa.codymaker.brand.domain.infra.BrandJpaInfra
import com.musinsa.codymaker.brand.domain.model.Brand
import com.musinsa.codymaker.brand.domain.repository.BrandRepository
import com.musinsa.codymaker.common.exception.NotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BrandServiceTest(private val jpaInfra: BrandJpaInfra) : BehaviorSpec({

    isolationMode = IsolationMode.InstancePerLeaf

    val brandRepo = BrandRepository(jpaInfra)
    val brandService = BrandService(brandRepo)

    Given("브랜드 정보가 주어지고") {
        val brandSaveReq = BrandSaveReq("Nike")

        When("등록하면") {
            val saveBrandId = brandService.saveBrand(brandSaveReq)

            Then("브랜드 ID를 반환 한다.") {
                saveBrandId shouldNotBe null
            }
        }
    }

    Given("브랜드가 저장되어 있고") {
        val brand = Brand("Nike", true)
        val savedBrand = brandRepo.save(brand)

        When("수정하면") {
            val brandUpdateReq = BrandUpdateReq("ABC")
            val updateBrandId = brandService.updateBrand(savedBrand.id!!, brandUpdateReq)

            Then("브랜드 ID를 반환 한다.") {
                updateBrandId shouldBe savedBrand.id
            }
        }

        When("수정 하려는 브랜드가 존재하지 않으면") {
            val brandUpdateReq = BrandUpdateReq("ABC")

            val exception = shouldThrow<NotFoundException> {
                brandService.updateBrand(30L, brandUpdateReq)
            }

            Then("NotFoundException 예외를 발생시킨다.") {
                exception.shouldBeInstanceOf<NotFoundException>()
            }
        }

        When("삭제하면") {
            brandService.deleteBrand(savedBrand.id!!)

            Then("브랜드의 삭제 여부가 True로 변경된다.") {
                val getBrand = brandRepo.getById(savedBrand.id!!)
                getBrand.deleted shouldBe true
            }
        }

        When("삭제 하려는 브랜드가 존재하지 않으면") {
            val exception = shouldThrow<NotFoundException> {
                brandService.deleteBrand(30L)
            }

            Then("NotFoundException 예외를 발생시킨다.") {
                exception.shouldBeInstanceOf<NotFoundException>()
            }
        }
    }

})
