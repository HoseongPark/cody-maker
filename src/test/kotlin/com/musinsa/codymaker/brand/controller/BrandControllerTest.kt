package com.musinsa.codymaker.brand.controller

import com.musinsa.codymaker.brand.service.BrandService
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(BrandController::class)
class BrandControllerTest(
    @Autowired val mockMvc: MockMvc,
    @MockkBean val brandService: BrandService
) : BehaviorSpec({

    Given("유효한 브랜드 정보가 주어지고") {
        every { brandService.saveBrand(any()) } returns 1L

        val jsonString = """
                {
                    "name": "Nike"
                }
            """.trimIndent()

        When("등록 API를 요청하면") {
            val perform = mockMvc.perform(
                post("/brand")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonString)
            )

            Then("200 Status와, 브랜드 ID를 반환한다") {
                perform
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.id").value(1))
            }
        }
    }

    Given("유효하지 않은 브랜드 정보가 주어지고") {
        val jsonString = """
                {
                    "name": ""
                }
            """.trimIndent()

        When("등록 API를 요청하면") {
            val perform = mockMvc.perform(
                post("/brand")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonString)
            )

            Then("400 Status를 반환한다") {
                perform
                    .andExpect(status().isBadRequest)
            }
        }
    }
})
