package com.musinsa.codymaker.brand.controller.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank


@Schema(description = "브랜드 정보 저장 요청 모델")
data class BrandSaveReq(

    @Schema(description = "브랜드 이름", example = "Nike")
    @field:NotBlank(message = "브랜드 이름은 필수입니다.")
    val name: String
)
