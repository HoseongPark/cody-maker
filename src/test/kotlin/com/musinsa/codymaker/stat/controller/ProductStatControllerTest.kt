package com.musinsa.codymaker.stat.controller

import com.musinsa.codymaker.common.exception.ApiExceptionHandler
import com.musinsa.codymaker.product.domain.model.Category
import com.musinsa.codymaker.stat.service.BrandMinPriceProductStatService
import com.musinsa.codymaker.stat.service.CategoryMinPriceProductStatService
import com.musinsa.codymaker.stat.service.CategoryProductStatService
import com.musinsa.codymaker.stat.service.model.BrandMinPriceProductStatDto
import com.musinsa.codymaker.stat.service.model.CategoryMinPriceProductStatDto
import com.musinsa.codymaker.stat.service.model.CategoryProductStatDto
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ProductStatController::class)
@Import(ApiExceptionHandler::class)
class ProductStatControllerTest(
    private val mockMvc: MockMvc,
    @MockkBean private val categoryProductStatService: CategoryProductStatService,
    @MockkBean private val brandMinPriceProductStatService: BrandMinPriceProductStatService,
    @MockkBean private val categoryMinPriceProductStatService: CategoryMinPriceProductStatService
) : BehaviorSpec({

    Given("카테고리가 주어지고") {
        val stat = CategoryProductStatDto(
            category = Category.TOP,
            minPrice = CategoryProductStatDto.StatData("NIKE", Category.TOP, 10000),
            maxPrice = CategoryProductStatDto.StatData("ADIDAS", Category.TOP, 20000)
        )

        every { categoryProductStatService.getStat(any()) } returns stat

        val category = "TOP"

        When("상품 통계 API를 요청하면") {
            val perform = mockMvc.perform(
                get("/stat/product/category-price")
                    .queryParam("category", category)
            )

            Then("200 Status와, 카테고리별 상품 통계를 반환한다") {
                perform
                    .andExpect(status().isOk)

                    .andExpect(jsonPath("$.category").value("TOP"))

                    .andExpect(jsonPath("$.minPrice[0].price").value(10000))
                    .andExpect(jsonPath("$.minPrice[0].brandName").value("NIKE"))

                    .andExpect(jsonPath("$.maxPrice[0].price").value(20000))
                    .andExpect(jsonPath("$.maxPrice[0].brandName").value("ADIDAS"))
            }
        }
    }

    Given("브랜드명이 주어지고") {
        val statDto = BrandMinPriceProductStatDto(
            brandName = "NIKE",
            minPrice = listOf(
                BrandMinPriceProductStatDto.StatData("NIKE", Category.TOP, 10_000),
                BrandMinPriceProductStatDto.StatData("NIKE", Category.BOTTOM, 20_000),
                BrandMinPriceProductStatDto.StatData("NIKE", Category.BAG, 30_000),
                BrandMinPriceProductStatDto.StatData("NIKE", Category.OUTER, 40_000),
                BrandMinPriceProductStatDto.StatData("NIKE", Category.SHOES, 50_000),
                BrandMinPriceProductStatDto.StatData("NIKE", Category.ACCESSORY, 60_000),
                BrandMinPriceProductStatDto.StatData("NIKE", Category.HAT, 70_000),
                BrandMinPriceProductStatDto.StatData("NIKE", Category.SOCKS, 80_000)
            ),
            totalPrice = 360_000,
        )

        every { brandMinPriceProductStatService.getStat(any()) } returns statDto

        val brandName = "NIKE"

        When("브랜드별 상품 통계 API를 요청하면") {
            val perform = mockMvc.perform(
                get("/stat/product/brand-product")
                    .queryParam("brandName", brandName)
            )

            Then("브랜드별 상품 통계를 반환한다") {
                perform
                    .andExpect(status().isOk)

                    .andExpect(jsonPath("$.minPrice.totalPrice").value(360_000))
                    .andExpect(jsonPath("$.minPrice.brandName").value("NIKE"))

                    .andExpect(jsonPath("$.minPrice.category[0].price").value(10_000))
                    .andExpect(jsonPath("$.minPrice.category[0].category").value("TOP"))
            }
        }
    }

    Given("카테고리별 통계 요청이 주어지고") {
        val stat = CategoryMinPriceProductStatDto(
            minPrice = listOf(
                CategoryMinPriceProductStatDto.Stat(
                    category = Category.TOP,
                    brandName = "NIKE",
                    price = 10_000
                ),
                CategoryMinPriceProductStatDto.Stat(
                    category = Category.BOTTOM,
                    brandName = "ADIDAS",
                    price = 20_000
                ),
                CategoryMinPriceProductStatDto.Stat(
                    category = Category.BAG,
                    brandName = "PUMA",
                    price = 30_000
                ),
                CategoryMinPriceProductStatDto.Stat(
                    category = Category.OUTER,
                    brandName = "REEBOK",
                    price = 40_000
                ),
                CategoryMinPriceProductStatDto.Stat(
                    category = Category.SHOES,
                    brandName = "UNDER ARMOUR",
                    price = 50_000
                ),
                CategoryMinPriceProductStatDto.Stat(
                    category = Category.ACCESSORY,
                    brandName = "VANS",
                    price = 60_000
                ),
                CategoryMinPriceProductStatDto.Stat(
                    category = Category.HAT,
                    brandName = "CONVERSE",
                    price = 70_000
                ),
                CategoryMinPriceProductStatDto.Stat(
                    category = Category.SOCKS,
                    brandName = "NEW BALANCE",
                    price = 80_000
                )
            ),
            totalPrice = 360_000
        )

        every { categoryMinPriceProductStatService.getStat() } returns stat

        When("카테고리별 최소 가격 상품 통계 API를 요청하면") {
            val perform = mockMvc.perform(
                get("/stat/product/category-min-price")
            )

            Then("카테고리별 최소 가격 상품 통계를 반환한다") {
                perform
                    .andExpect(status().isOk)

                    .andExpect(jsonPath("$.totalPrice").value(360_000))

                    .andExpect(jsonPath("$.stats[0].category").value("TOP"))
                    .andExpect(jsonPath("$.stats[0].price").value(10_000))
            }
        }

    }
})
