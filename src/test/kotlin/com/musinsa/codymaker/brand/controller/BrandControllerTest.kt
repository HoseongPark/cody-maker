package com.musinsa.codymaker.brand.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.musinsa.codymaker.brand.controller.model.BrandSaveReq
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
    @Autowired val objectMapper: ObjectMapper,
    @MockkBean val brandService: BrandService
) : BehaviorSpec({

    isolationMode = IsolationMode.InstancePerLeaf

    extensions(listOf(SpringExtension))

    Given("브랜드 등록 API 호출시") {
        When("브랜드 저장 모델이 주어지면") {
            val brandSaveReq = BrandSaveReq("Nike")

            every { brandService.saveBrand(brandSaveReq) } returns 1L

            val perform = mockMvc.perform(
                post("/brand")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(brandSaveReq))
            )

            Then("200 Status와, 브랜드 ID를 반환한다") {
                perform
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.id").value(1))
            }
        }

        When("필수 값이 존재하지 않으면") {
            val brandSaveReq = BrandSaveReq("")

            every { brandService.saveBrand(brandSaveReq) } returns 1L

            val perform = mockMvc.perform(
                post("/brand")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(brandSaveReq))
            )

            Then("400 Status를 반환한다") {
                perform
                    .andExpect(status().isBadRequest)
            }
        }
    }
})
