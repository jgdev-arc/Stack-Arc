package com.tlz.stackarc.exceptions

import com.tlz.stackarc.dtos.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception): ResponseEntity<com.tlz.stackarc.dtos.Response> {
        val response = com.tlz.stackarc.dtos.Response(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = ex.message ?: "An unexpected error occurred"
        )

        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(com.tlz.stackarc.exceptions.NotFoundException::class)
    fun handleNotFoundException(ex: com.tlz.stackarc.exceptions.NotFoundException): ResponseEntity<com.tlz.stackarc.dtos.Response> {
        val response = com.tlz.stackarc.dtos.Response(
            status = HttpStatus.NOT_FOUND.value(),
            message = ex.message ?: "An unexpected error occurred"
        )

        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(com.tlz.stackarc.exceptions.NameValueRequiredException::class)
    fun handleNameValueRequiredException(ex: com.tlz.stackarc.exceptions.NameValueRequiredException): ResponseEntity<com.tlz.stackarc.dtos.Response> {
        val response = com.tlz.stackarc.dtos.Response(
            status = HttpStatus.BAD_REQUEST.value(),
            message = ex.message ?: "An unexpected error occurred"
        )

        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(com.tlz.stackarc.exceptions.InvalidCredentialsException::class)
    fun handleInvalidCredentialsException(ex: com.tlz.stackarc.exceptions.InvalidCredentialsException): ResponseEntity<com.tlz.stackarc.dtos.Response> {
        val response = com.tlz.stackarc.dtos.Response(
            status = HttpStatus.BAD_REQUEST.value(),
            message = ex.message ?: "An unexpected error occurred"
        )

        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

}
