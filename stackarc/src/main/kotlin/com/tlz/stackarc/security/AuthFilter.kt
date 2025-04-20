package com.tlz.stackarc.security

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class AuthFilter(
    private val jwtUtils: JwtUtils,
    private val customUserDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(AuthFilter::class.java)

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val token = getTokenFromRequest(request)

            if (!token.isNullOrBlank()) {
                val email = jwtUtils.getUsernameFromToken(token)

                if (StringUtils.hasText(email) && SecurityContextHolder.getContext().authentication == null) {
                    val userDetails: UserDetails = customUserDetailsService.loadUserByUsername(email)

                    if (jwtUtils.isTokenValid(token, userDetails)) {
                        log.info("Valid Token for user: $email")

                        val authenticationToken = UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.authorities
                        )

                        authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)

                        SecurityContextHolder.getContext().authentication = authenticationToken
                    }
                }
            }

            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            log.error("Exception occurred in AuthFilter: ${e.message}", e)
            filterChain.doFilter(request, response)
        }
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String? =
        request.getHeader("Authorization")
            ?.takeIf { it.startsWith("Bearer ") }
            ?.substring(7)
}
