package com.musinsa.codymaker.product.service

import com.musinsa.codymaker.brand.domain.repository.BrandRepository
import com.musinsa.codymaker.product.controller.model.ProductSaveReq
import com.musinsa.codymaker.product.controller.model.ProductUpdateReq
import com.musinsa.codymaker.product.domain.model.Category
import com.musinsa.codymaker.product.domain.model.Product
import com.musinsa.codymaker.product.domain.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepo: ProductRepository,
    private val brandRepo: BrandRepository
) {

    /**
     * 상품 등록이후 사용되어야 필드에 대해 명시 되어 있지 않아.
     * 등록된 상품의 ID를 반환 하도록 구성.
     *
     * - 해당 ID를 통해 상품에 대한 정보를 조회 할 수 있음.
     * - 만약 추가적인 속성이 필요하다면, 추가적인 필드를 명시하여 반환하도록 수정.
     */
    @Transactional
    fun saveProduct(req: ProductSaveReq): Long {
        val brand = brandRepo.getById(req.brandId)

        // Brand 조회시 예외가 발생하지 않았다면 Brand ID는 무조건 존재
        val product = Product(
            brandId = brand.id!!,
            category = Category.from(req.category),
            price = req.price
        )

        val savedProduct = productRepo.save(product)

        // 저장시 예외가 발생하지 않았다면 ID는 무조건 존재
        return savedProduct.id!!
    }

    @Transactional
    fun updateProduct(id: Long, req: ProductUpdateReq): Long {
        val product = productRepo.getById(id)

        product.update(
            category = req.category?.let { Category.from(it) },
            price = req.price
        )

        // 조회 및 수정시 예외가 발생하지 않았다면 ID는 무조건 존재
        return product.id!!
    }

}