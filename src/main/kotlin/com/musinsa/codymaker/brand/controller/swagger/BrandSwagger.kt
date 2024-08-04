package com.musinsa.codymaker.brand.controller.swagger

import com.musinsa.codymaker.brand.controller.model.BrandSaveReq
import com.musinsa.codymaker.brand.controller.model.BrandSaveRes
import org.springframework.http.ResponseEntity

interface BrandSwagger {

    fun saveBrand(saveRequest: BrandSaveReq): ResponseEntity<BrandSaveRes>
}