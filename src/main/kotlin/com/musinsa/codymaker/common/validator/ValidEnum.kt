package com.musinsa.codymaker.common.validator

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * Enum 클래스의 유효섬을 검증하기 위한 Custom Validation 사용을 위한 Annotation
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidEnumValidator::class])
annotation class ValidEnum(
    val message: String = "Invalid value",
    val enumClass: KClass<out Enum<*>>,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
