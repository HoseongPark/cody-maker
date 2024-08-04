package com.musinsa.codymaker.brand.service

import com.musinsa.codymaker.brand.controller.model.BrandSaveReq
import com.musinsa.codymaker.brand.controller.model.BrandUpdateReq
import com.musinsa.codymaker.brand.domain.model.Brand
import com.musinsa.codymaker.brand.domain.repository.BrandRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BrandService(private val brandRepo: BrandRepository) {

    /**
     * 브랜드 등록이후 사용되어야 필드에 대해 명시 되어 있지 않아.
     * 등록된 브랜드의 ID를 반환 하도록 구성.
     *
     * - 해당 ID를 통해 브랜드에 대한 정보를 조회 할 수 있음.
     * - 만약 추가적인 속성이 필요하다면, 추가적인 필드를 명시하여 반환하도록 수정.
     */
    @Transactional
    fun saveBrand(req: BrandSaveReq): Long {
        // 브랜드 등록시 활성화 상태로 등록
        val brand = Brand(req.name, false)
        val savedBrand = brandRepo.save(brand)

        // save 로직이 오류 없이 진행 되었을 경우 해당 ID는 항상 존재한다.
        return savedBrand.id!!
    }

    /**
     * 브랜드 수정 이후 사용되어야 필드에 대해 명시 되어 있지 않아.
     * 수정된 브랜드의 ID를 반환 하도록 구성.
     */
    @Transactional
    fun updateBrand(id: Long, req: BrandUpdateReq): Long {
        val brand = brandRepo.getById(id)
        brand.update(req.name)

        // update 로직이 오류 없이 진행 되었을 경우 해당 ID는 항상 존재한다.
        return brand.id!!
    }

    @Transactional
    fun deleteBrand(id: Long) {
        val brand = brandRepo.getById(id)
        brand.delete()
    }
}