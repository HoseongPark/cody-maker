package com.musinsa.codymaker.product.domain.model

enum class Category {
    TOP,
    BOTTOM,
    OUTER,
    SHOES,
    BAG,
    HAT,
    SOCKS,
    ACCESSORY;

    companion object {
        fun from(source: String): Category = Category.valueOf(source.uppercase())
    }
}