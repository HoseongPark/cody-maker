package com.musinsa.codymaker.product.controller.model

import com.musinsa.codymaker.common.validator.ValidEnum
import com.musinsa.codymaker.product.domain.model.Category
import jakarta.validation.constraints.NotNull

data class ProductSaveReq(

    @field:NotNull(message = "브랜드 ID는 필수입니다.")
    val brandId: Long,

    @field:NotNull(message = "상품 카테고리는 필수입니다.")
    @field:ValidEnum(enumClass = Category::class, message = "상품 카테고리가 유효하지 않습니다.")
    val category: String,

    @field:NotNull(message = "상품 가격은 필수입니다.")
    val price: Int
)
