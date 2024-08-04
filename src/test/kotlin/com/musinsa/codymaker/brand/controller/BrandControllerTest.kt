package com.musinsa.codymaker.brand.controller

import com.musinsa.codymaker.brand.service.BrandService
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
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

    isolationMode = IsolationMode.InstancePerLeaf
    extensions(listOf(SpringExtension))

    Given("브랜드 등록 API 요청시") {
        When("유효한 브랜드 정보가 전달되면") {
            val jsonString = """
                {
                    "name": "Nike"
                }
            """.trimIndent()

            every { brandService.saveBrand(any()) } returns 1L

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

        When("유효 하지 않은 브랜드 정보가 전달 되면") {
            val jsonString = """
                {
                    "name": ""
                }
            """.trimIndent()

            every { brandService.saveBrand(any()) } returns 1L

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
