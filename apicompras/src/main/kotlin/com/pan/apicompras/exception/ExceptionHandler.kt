package com.pan.apicompras.exception

import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandlerAdvice {
    @ExceptionHandler(ResourceException::class)
    fun handleException(e: ResourceException): ResponseEntity<*> {
        return ResponseEntity.status(e.httpStatus).body(e.message)
    }
}