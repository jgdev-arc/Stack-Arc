package com.tlz.Stack_Arc.exceptions

import com.fasterxml.jackson.databind.ObjectMapper
import com.tlz.Stack_Arc.dtos.Response
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val errorResponse = Response(
            status = HttpStatus.UNAUTHORIZED.value(),
            message = authException.message ?: "Unauthorized access"
        )

        response.contentType = "application/json"
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }
}
