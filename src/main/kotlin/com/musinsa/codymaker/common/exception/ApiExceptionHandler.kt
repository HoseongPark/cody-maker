package com.musinsa.codymaker.common.exception

import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ApiErrorRes> {
        val apiErrorRes = ApiErrorRes(ex.message)
        return ResponseEntity.status(NOT_FOUND).body(apiErrorRes)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ApiErrorRes> {
        val apiErrorRes = ApiErrorRes(ex.message)
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(apiErrorRes)
    }
}