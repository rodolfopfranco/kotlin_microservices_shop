package com.pan.mib.feign.exceptions

import feign.FeignException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandlerAdvice {
    @ExceptionHandler(FeignException::class)
    fun handleException(e: FeignException): ResponseEntity<*> {
        return ResponseEntity.status(e.status()).body(decodeMessage(e.message))
    }

    fun decodeMessage(message: String?) : String = message?.split(")]: ")?.get(1) ?: ""
}