package com.tlz.Stack_Arc.exceptions

import com.fasterxml.jackson.databind.ObjectMapper
import com.tlz.Stack_Arc.dtos.Response
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class CustomAccessDenialHandler(
    private val objectMapper: ObjectMapper
) : AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        val errorResponse = Response(
            status = HttpStatus.FORBIDDEN.value(),
            message = accessDeniedException.message ?: "Access is denied"
        )

        response.contentType = "application/json"
        response.status = HttpStatus.FORBIDDEN.value()
        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }
}
