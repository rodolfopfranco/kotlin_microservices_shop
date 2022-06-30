package com.pan.apiprodutos.exception

import org.springframework.http.HttpStatus


class ResourceException(httpStatus: HttpStatus, message: String?) : RuntimeException(message) {
    var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
    init {
        this.httpStatus = httpStatus
    }
}