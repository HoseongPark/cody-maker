package com.musinsa.codymaker.common.validator

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

/**
 * Enum 클래스의 유효섬을 검증하기 위한 Custom Validation
 */
class NullableValidEnumValidator : ConstraintValidator<NullableValidEnum, String> {

    private lateinit var enumClass: Array<out Enum<*>>

    override fun initialize(constraintAnnotation: NullableValidEnum) {
        enumClass = constraintAnnotation.enumClass.java.enumConstants
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) return true
        return enumClass.any { it.name == value.uppercase() }
    }
}