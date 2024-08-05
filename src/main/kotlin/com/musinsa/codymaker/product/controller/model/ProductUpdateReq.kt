package com.musinsa.codymaker.product.controller.model

import com.musinsa.codymaker.common.validator.NullableValidEnum
import com.musinsa.codymaker.product.domain.model.Category

data class ProductUpdateReq(

    @field:NullableValidEnum(enumClass = Category::class, message = "상품 카테고리가 유효하지 않습니다.")
    val category: String?,

    val price: Int?
)
