package com.musinsa.codymaker.brand.controller.swagger

import com.musinsa.codymaker.brand.controller.model.BrandSaveReq
import com.musinsa.codymaker.brand.controller.model.BrandSaveRes
import com.musinsa.codymaker.brand.controller.model.BrandUpdateReq
import com.musinsa.codymaker.brand.controller.model.BrandUpdateRes
import com.musinsa.codymaker.common.exception.ApiErrorRes
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "Brand", description = "Brand API")
interface BrandSwagger {

    @Tag(name = "Brand")
    @Operation(summary = "브랜드 정보 저장", description = "브랜드 정보를 저장합니다.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(
            responseCode = "500", description = "Internal Server Error",
            content = [Content(schema = Schema(implementation = ApiErrorRes::class))]
        )
    )
    fun saveBrand(saveRequest: BrandSaveReq): ResponseEntity<BrandSaveRes>

    @Tag(name = "Brand")
    @Operation(summary = "브랜드 정보 수정", description = "브랜드 정보를 수정합니다..")
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
    fun updateBrand(id: Long, updateRequest: BrandUpdateReq): ResponseEntity<BrandUpdateRes>
}