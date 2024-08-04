package com.musinsa.codymaker.brand.controller

import com.musinsa.codymaker.brand.controller.model.BrandSaveReq
import com.musinsa.codymaker.brand.controller.model.BrandSaveRes
import com.musinsa.codymaker.brand.controller.swagger.BrandSwagger
import com.musinsa.codymaker.brand.service.BrandService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/brand")
class BrandController(private val brandService: BrandService) : BrandSwagger {

    @PostMapping
    override fun saveBrand(@Valid @RequestBody saveRequest: BrandSaveReq): ResponseEntity<BrandSaveRes> {
        val brandId = brandService.saveBrand(saveRequest)
        val response = BrandSaveRes(brandId)

        return ResponseEntity.ok().body(response)
    }

}