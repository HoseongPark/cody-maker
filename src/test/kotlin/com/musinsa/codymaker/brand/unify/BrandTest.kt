package com.musinsa.codymaker.brand.unify

import com.fasterxml.jackson.databind.ObjectMapper
import com.musinsa.codymaker.brand.controller.model.BrandSaveReq
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class BrandTest(
    @Autowired mockMvc: MockMvc,
    @Autowired objectMapper: ObjectMapper
) : BehaviorSpec({

    extensions(listOf(SpringExtension))

    Given("브랜드 등록 테스트") {
        When("브랜드 저장 모델이 주어지면") {
            val brandSaveReq = BrandSaveReq("Nike")

            val perform = mockMvc.perform(
                post("/brand")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(brandSaveReq))
            )

            Then("200 Status와, 브랜드 ID를 반환한다") {
                perform
                    .andExpect(status().isOk)
            }
        }

        When("브랜드 저장 요청의 필수 값이 없다면") {
            val brandSaveReq = BrandSaveReq("")

            val perform = mockMvc.perform(
                post("/brand")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(brandSaveReq))
            )

            Then("400 status 반환한다.") {
                perform
                    .andExpect(status().isBadRequest)
            }
        }
    }
})