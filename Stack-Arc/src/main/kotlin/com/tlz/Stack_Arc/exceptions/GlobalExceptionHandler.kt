package com.tlz.Stack_Arc.exceptions

import com.tlz.Stack_Arc.dtos.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception): ResponseEntity<Response> {
        val response = Response(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            message = ex.message ?: "An unexpected error occurred"
        )

        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<Response> {
        val response = Response(
            status = HttpStatus.NOT_FOUND.value(),
            message = ex.message ?: "An unexpected error occurred"
        )

        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(NameValueRequiredException::class)
    fun handleNameValueRequiredException(ex: NameValueRequiredException): ResponseEntity<Response> {
        val response = Response(
            status = HttpStatus.BAD_REQUEST.value(),
            message = ex.message ?: "An unexpected error occurred"
        )

        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidCredentialsException::class)
    fun handleInvalidCredentialsException(ex: InvalidCredentialsException): ResponseEntity<Response> {
        val response = Response(
            status = HttpStatus.BAD_REQUEST.value(),
            message = ex.message ?: "An unexpected error occurred"
        )

        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

}
