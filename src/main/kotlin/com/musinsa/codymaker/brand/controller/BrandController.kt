package com.musinsa.codymaker.brand.controller

import com.musinsa.codymaker.brand.controller.model.BrandSaveReq
import com.musinsa.codymaker.brand.controller.model.BrandSaveRes
import com.musinsa.codymaker.brand.controller.model.BrandUpdateReq
import com.musinsa.codymaker.brand.controller.model.BrandUpdateRes
import com.musinsa.codymaker.brand.controller.swagger.BrandSwagger
import com.musinsa.codymaker.brand.service.BrandService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/brand")
class BrandController(private val brandSvc: BrandService) : BrandSwagger {

    @PostMapping
    override fun saveBrand(@Valid @RequestBody saveRequest: BrandSaveReq): ResponseEntity<BrandSaveRes> {
        val brandId = brandSvc.saveBrand(saveRequest)
        val response = BrandSaveRes(brandId)

        return ResponseEntity.ok().body(response)
    }

    @PatchMapping("/{brand-id}")
    override fun updateBrand(
        @PathVariable("brand-id") id: Long,
        @RequestBody updateRequest: BrandUpdateReq
    ): ResponseEntity<BrandUpdateRes> {
        val brandId = brandSvc.updateBrand(id, updateRequest)
        val response = BrandUpdateRes(brandId)

        return ResponseEntity.ok().body(response)
    }

    @DeleteMapping("/{brand-id}")
    override fun deleteBrand(@PathVariable("brand-id") id: Long): ResponseEntity<Unit> {
        brandSvc.deleteBrand(id)
        return ResponseEntity.ok().build()
    }

}