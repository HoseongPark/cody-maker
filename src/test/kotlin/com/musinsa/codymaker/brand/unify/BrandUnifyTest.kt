package com.musinsa.codymaker.brand.unify

import com.fasterxml.jackson.databind.ObjectMapper
import com.musinsa.codymaker.brand.controller.model.BrandSaveReq
import com.musinsa.codymaker.brand.controller.model.BrandUpdateReq
import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class BrandUnifyTest(
    @Autowired mockMvc: MockMvc,
    @Autowired objectMapper: ObjectMapper
) : BehaviorSpec({

    Given("브랜드 정보가 주어지고") {
        val saveReq = BrandSaveReq("Nike")

        When("등록하면") {
            val perform = mockMvc.perform(
                post("/brand")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(saveReq))
            )

            Then("200 Status와, 브랜드 ID를 반환한다") {
                perform
                    .andExpect(status().isOk)
            }
        }
    }

    Given("잘못된 브랜드 정보가 주어지고") {
        val brandSaveReq = BrandSaveReq("")

        When("등록하면") {
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

    Given("수정 정보가 주어지고") {
        val saveReq = BrandSaveReq("Nike")

        mockMvc.perform(
            post("/brand")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saveReq))
        )

        val updateReq = BrandUpdateReq(name = "Nike")

        When("수정하면") {
            val perform = mockMvc.perform(
                patch("/brand/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateReq))
            )

            Then("200 Status와, 브랜드 ID를 반환한다") {
                perform
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.id").value(1))
            }
        }

        When("존재하지 않는 브랜드를 수정하면") {
            val perform = mockMvc.perform(
                patch("/brand/32")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateReq))
            )

            Then("404 status 반환한다.") {
                perform
                    .andExpect(status().isNotFound)
            }
        }
    }

})