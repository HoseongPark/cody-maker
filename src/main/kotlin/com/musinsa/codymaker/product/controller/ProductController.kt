package com.musinsa.codymaker.product.controller

import com.musinsa.codymaker.product.controller.model.ProductSaveReq
import com.musinsa.codymaker.product.controller.model.ProductSaveRes
import com.musinsa.codymaker.product.controller.swagger.ProductSwagger
import com.musinsa.codymaker.product.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class ProductController(private val productSvc: ProductService) : ProductSwagger {

    @PostMapping
    override fun saveProduct(@Valid @RequestBody saveRequest: ProductSaveReq): ResponseEntity<ProductSaveRes> {
        val productId = productSvc.saveProduct(saveRequest)
        val response = ProductSaveRes(productId)

        return ResponseEntity.ok().body(response)
    }

}