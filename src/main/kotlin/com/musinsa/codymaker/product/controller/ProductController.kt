package com.musinsa.codymaker.product.controller

import com.musinsa.codymaker.product.controller.model.ProductSaveReq
import com.musinsa.codymaker.product.controller.model.ProductSaveRes
import com.musinsa.codymaker.product.controller.model.ProductUpdateReq
import com.musinsa.codymaker.product.controller.model.ProductUpdateRes
import com.musinsa.codymaker.product.controller.swagger.ProductSwagger
import com.musinsa.codymaker.product.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController(private val productSvc: ProductService) : ProductSwagger {

    @PostMapping
    override fun saveProduct(@Valid @RequestBody saveRequest: ProductSaveReq): ResponseEntity<ProductSaveRes> {
        val productId = productSvc.saveProduct(saveRequest)
        val response = ProductSaveRes(productId)

        return ResponseEntity.ok().body(response)
    }

    @PatchMapping("/{product-id}")
    override fun updateProduct(
        @PathVariable("product-id") id: Long,
        @Valid @RequestBody updateRequest: ProductUpdateReq
    ): ResponseEntity<ProductUpdateRes> {
        val productId = productSvc.updateProduct(id, updateRequest)
        val response = ProductUpdateRes(productId)

        return ResponseEntity.ok().body(response)
    }

    @DeleteMapping("/{product-id}")
    override fun deleteProduct(@PathVariable("product-id") id: Long): ResponseEntity<Unit> {
        productSvc.deleteProduct(id)

        return ResponseEntity.ok().build()
    }

}