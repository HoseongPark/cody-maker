package com.musinsa.codymaker.brand.controller.swagger

import com.musinsa.codymaker.brand.controller.model.BrandSaveReq
import com.musinsa.codymaker.brand.controller.model.BrandSaveRes
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

@Tag(name = "Brand", description = "Brand API")
interface BrandSwagger {

    @Tag(name = "Brand", description = "Brand 관리를 위한 API 입니다.")
    @Operation(summary = "브랜드 정보 저장", description = "브랜드 정보를 저장합니다.")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "500", description = "Internal Server Error")
    )
    fun saveBrand(saveRequest: BrandSaveReq): ResponseEntity<BrandSaveRes>
}