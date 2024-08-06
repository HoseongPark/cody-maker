package com.musinsa.codymaker.stat.controller.swagger

import com.musinsa.codymaker.common.exception.ApiErrorRes
import com.musinsa.codymaker.product.domain.model.Category
import com.musinsa.codymaker.stat.controller.model.BrandMinPriceProductStatRes
import com.musinsa.codymaker.stat.controller.model.CategoryMinPriceProductStatRes
import com.musinsa.codymaker.stat.controller.model.CategoryProductStatRes
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "Stat", description = "Stat API")
interface ProductStatSwagger {

    @Tag(name = "Stat")
    @Operation(summary = "특정 카테고리 상품 통계", description = "특정 카테고리의 상품 통계 정보를 제공 합니다.")
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
    fun getCategoryPriceStat(category: Category): ResponseEntity<CategoryProductStatRes>

    @Tag(name = "Stat")
    @Operation(summary = "특정 브랜드 상품 통계", description = "특정 브랜드의 상품에 대한 통계 정보를 제공 합니다.")
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
    fun getBrandProductStat(brandName: String): ResponseEntity<BrandMinPriceProductStatRes>

    @Tag(name = "Stat")
    @Operation(summary = "모든 카테고리별 최저 가격 통계", description = "모든 카테고리별 최저 가격의 상품에 대한 통계 정보를 제공 합니다.")
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
    fun getCategoryMinPriceStat(): ResponseEntity<CategoryMinPriceProductStatRes>
}