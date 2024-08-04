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
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BrandServiceTest(private val jpaInfra: BrandJpaInfra) : BehaviorSpec({

    extensions(SpringTestExtension(SpringTestLifecycleMode.Root))
    isolationMode = IsolationMode.InstancePerLeaf

    val brandRepo = BrandRepository(jpaInfra)

    val brandService = BrandService(brandRepo)

    Given("브랜드 등록") {
        When("정상적으로 등록이 되었을 때") {
            every { brandRepo.save(any()) } returns mockk<Brand> { every { id } returns 1L }

            val brandSaveReq = BrandSaveReq("Nike")
            val saveBrandId = brandService.saveBrand(brandSaveReq)

            Then("브랜드 ID를 반환 한다.") {
                saveBrandId shouldBe 1L
            }
        }

        When("등록 중 에러가 발생했을 때") {
            every { brandRepo.save(any()) } throws RuntimeException()

            val brandSaveReq = BrandSaveReq("Nike")
            val exception = shouldThrow<RuntimeException> { brandService.saveBrand(brandSaveReq) }

            Then("예외를 발생시킨다.") {
                exception.shouldBeInstanceOf<RuntimeException>()
            }
        }
    }

    Given("브랜드 수정") {
        When("정상적으로 수정이 되었을 때") {
            val saveBrand = brandRepo.save(Brand("Nike", true))

            val brandUpdateReq = BrandUpdateReq("ABC")
            val updateBrandId = brandService.updateBrand(saveBrand.id!!, brandUpdateReq)

            Then("브랜드 ID를 반환 한다.") {
                updateBrandId shouldBe 1L
            }
        }

        When("수정 하려는 브랜드가 존재하지 않았을 때") {
            brandRepo.save(Brand("Nike", true))

            val brandUpdateReq = BrandUpdateReq("ABC")
            val exception = shouldThrow<NotFoundException> { brandService.updateBrand(30L, brandUpdateReq) }

            Then("NotFoundException 예외를 발생시킨다.") {
                exception.shouldBeInstanceOf<NotFoundException>()
            }
        }
    }

    Given("브랜드 삭제") {
        When("정상적으로 삭제가 되었을 때") {
            val saveBrand = brandRepo.save(Brand("Nike", false))

            brandService.deleteBrand(saveBrand.id!!)

            val brand = brandRepo.getById(saveBrand.id!!)

            Then("브랜드의 삭제 여부가 True로 변경된다.") {
                brand.deleted shouldBe true
            }
        }

        When("삭제 하려는 브랜드가 존재하지 않았을 때") {
            brandRepo.save(Brand("Nike", false))

            val exception = shouldThrow<NotFoundException> { brandService.deleteBrand(30L) }

            Then("NotFoundException 예외를 발생시킨다.") {
                exception.shouldBeInstanceOf<NotFoundException>()
            }
        }
    }
})
