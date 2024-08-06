package com.musinsa.codymaker.product.domain.repository

import com.musinsa.codymaker.common.exception.NotFoundException
import com.musinsa.codymaker.product.domain.infra.ProductJpaInfra
import com.musinsa.codymaker.product.domain.model.Category
import com.musinsa.codymaker.product.domain.model.Product
import com.musinsa.codymaker.product.domain.model.QProduct
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class ProductRepository(
    private val productJpaInfra: ProductJpaInfra,
    private val queryFactory: JPAQueryFactory
) {

    private val qProduct = QProduct.product

    fun save(product: Product) = productJpaInfra.save(product)

    fun getById(id: Long): Product {
        val product = productJpaInfra.findById(id).orElseThrow { throw NotFoundException("Product not found") }
        return product
    }

    fun findMinPrice(category: Category? = null, brandId: Long? = null): Int {
        val maxPrice = queryFactory.select(qProduct.price.min())
            .from(qProduct)
            .where(equalsCategory(category), equalsBrandId(brandId))
            .fetchOne() ?: 0

        return maxPrice
    }

    fun findMaxPrice(category: Category? = null, brandId: Long? = null): Int {
        val maxPrice = queryFactory.select(qProduct.price.max())
            .from(qProduct)
            .where(equalsCategory(category), equalsBrandId(brandId))
            .fetchOne() ?: 0

        return maxPrice
    }

    fun findByCategoryAndPrice(category: Category, price: Int): List<Product> {
        val result = queryFactory.selectFrom(qProduct)
            .where(qProduct.category.eq(category))
            .where(qProduct.price.eq(price))
            .fetch()

        return result
    }

    private fun equalsCategory(category: Category?): BooleanExpression? {
        val categoryFilter = category?.let { qProduct.category.eq(category) }
        return categoryFilter
    }

    private fun equalsBrandId(brandId: Long?): BooleanExpression? {
        val brandFilter = brandId?.let { qProduct.brandId.eq(brandId) }
        return brandFilter
    }

}