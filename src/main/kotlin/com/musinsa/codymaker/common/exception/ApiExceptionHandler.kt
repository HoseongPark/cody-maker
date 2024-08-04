package com.musinsa.codymaker.common.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ApiErrorRes> {
        val errors = ex.bindingResult.allErrors.map { error ->
            (error as FieldError).field + ": " + error.defaultMessage
        }

        val apiError = ApiErrorRes("Validation Failed", errors)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError)
    }

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