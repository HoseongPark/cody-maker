package com.musinsa.codymaker.brand.service

import com.musinsa.codymaker.brand.controller.model.BrandSaveReq
import com.musinsa.codymaker.brand.domain.model.Brand
import com.musinsa.codymaker.brand.domain.repository.BrandRepository
import org.springframework.stereotype.Service

@Service
class BrandService(private val brandRepo: BrandRepository) {

    /**
     * 브랜드 등록이후 사용되어야 필드에 대해 명시 되어 있지 않아.
     * 등록된 브랜드의 ID를 반환 하도록 구성.
     *
     * - 해당 ID를 통해 브랜드에 대한 정보를 조회 할 수 있음.
     * - 만약 추가적인 속성이 필요하다면, 추가적인 필드를 명시하여 반환하도록 수정.
     */
    fun saveBrand(req: BrandSaveReq): Long {
        // 브랜드 등록시 활성화 상태로 등록
        val brand = Brand(req.name, true)
        val savedBrand = brandRepo.save(brand)
        val id = savedBrand.id ?: throw RuntimeException("Brand ID is null")

        return id
    }
}