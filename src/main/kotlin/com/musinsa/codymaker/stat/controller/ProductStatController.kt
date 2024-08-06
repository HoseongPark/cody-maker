package com.musinsa.codymaker.stat.controller

import com.musinsa.codymaker.product.domain.model.Category
import com.musinsa.codymaker.stat.controller.model.BrandMinPriceProductStatRes
import com.musinsa.codymaker.stat.controller.model.CategoryMinPriceProductStatRes
import com.musinsa.codymaker.stat.controller.model.CategoryProductStatRes
import com.musinsa.codymaker.stat.service.BrandMinPriceProductStatService
import com.musinsa.codymaker.stat.service.CategoryMinPriceProductStatService
import com.musinsa.codymaker.stat.service.CategoryProductStatService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/stat/product")
class ProductStatController(
    private val productCategoryStatSvc: CategoryProductStatService,
    private val brandProductStatSvc: BrandMinPriceProductStatService,
    private val categoryMinPriceStatSvc: CategoryMinPriceProductStatService
) {

    @GetMapping("/category-price")
    fun getCategoryPriceStat(@RequestParam category: Category): ResponseEntity<CategoryProductStatRes> {
        val productStat = productCategoryStatSvc.getStat(category)

        val response = CategoryProductStatRes.from(productStat)

        return ResponseEntity.ok(response)
    }

    @GetMapping("/brand-product")
    fun getBrandProductStat(@RequestParam brandName: String): ResponseEntity<BrandMinPriceProductStatRes> {
        val productStat = brandProductStatSvc.getStat(brandName)

        val response = BrandMinPriceProductStatRes.from(productStat)

        return ResponseEntity.ok(response)
    }

    @GetMapping("/category-min-price")
    fun getCategoryMinPriceStat(): ResponseEntity<CategoryMinPriceProductStatRes> {
        val productStat = categoryMinPriceStatSvc.getStat()

        val response = CategoryMinPriceProductStatRes.from(productStat)

        return ResponseEntity.ok(response)
    }
}