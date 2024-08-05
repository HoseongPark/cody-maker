package com.musinsa.codymaker.product.controller

import com.musinsa.codymaker.product.service.ProductService
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ProductController::class)
class ProductControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @MockkBean private val productSvc: ProductService
) : BehaviorSpec({

    Given("유효한 상품 정보가 주어지고") {
        every { productSvc.saveProduct(any()) } returns 1L

        val requestJson = """
                {
                    "brandId": 1,
                    "category": "TOP",
                    "price": 100000
                }
            """.trimIndent()

        When("등록 API를 요청하면") {
            val perform = mockMvc.perform(
                post("/product")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson)
            )

            Then("200 Status와, 상품 ID를 반환한다") {
                perform
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.id").value(1))
            }
        }
    }

    Given("유효하지 않은 상품 정보가 주어지고") {
        val requestJson = """
                {
                    "brandId": 1,
                    "category": "TEST",
                    "price": 100000
                }
            """.trimIndent()

        When("등록 API를 요청하면") {
            val perform = mockMvc.perform(
                post("/product")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson)
            )

            Then("400 Status를 반환한다") {
                perform
                    .andExpect(status().isBadRequest)
            }
        }
    }

    Given("유효한 상품 수정 정보가 주어지고") {
        every { productSvc.updateProduct(any(), any()) } returns 1L

        val requestJson = """
                {
                    "category": "TOP",
                    "price": 100000
                }
            """.trimIndent()

        When("수정 API를 요청하면") {
            val perform = mockMvc.perform(
                patch("/product/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson)
            )

            Then("200 Status와, 상품 ID를 반환한다") {
                perform
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.id").value(1))
            }
        }

    }
})
