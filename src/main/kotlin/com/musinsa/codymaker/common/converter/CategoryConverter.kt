package com.musinsa.codymaker.common.converter

import com.musinsa.codymaker.product.domain.model.Category
import org.springframework.core.convert.converter.Converter

class CategoryConverter : Converter<String, Category> {

    override fun convert(source: String): Category {
        val category = runCatching { Category.from(source) }
            .getOrElse { throw IllegalArgumentException("Invalid category: $source") }

        return category
    }
}