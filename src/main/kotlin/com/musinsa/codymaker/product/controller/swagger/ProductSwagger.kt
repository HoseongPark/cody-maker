package com.musinsa.codymaker.product.controller.swagger

import com.musinsa.codymaker.common.exception.ApiErrorRes
import com.musinsa.codymaker.product.controller.model.ProductSaveReq
import com.musinsa.codymaker.product.controller.model.ProductSaveRes
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "Product", description = "Product API")
interface ProductSwagger {

    @Tag(name = "Product")
    @Operation(summary = "상품 정보 저장", description = "상품 정보를 저장합니다.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(
            responseCode = "404", description = "Not Found Error",
            content = [Content(schema = Schema(implementation = ApiErrorRes::class))]
        ),
        ApiResponse(
            responseCode = "500", description = "Internal Server Error",
            content = [Content(schema = Schema(implementation = ApiErrorRes::class))]
        )
    )
    fun saveProduct(saveRequest: ProductSaveReq): ResponseEntity<ProductSaveRes>
}