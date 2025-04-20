package com.tlz.stackarc.controllers

import com.tlz.stackarc.dtos.LoginRequest
import com.tlz.stackarc.dtos.RegisterRequest
import com.tlz.stackarc.dtos.Response
import com.tlz.stackarc.services.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userService: UserService
) {

    @PostMapping("/register")
    fun registerUser(
        @RequestBody @Valid registerRequest: RegisterRequest
    ): ResponseEntity<Response> {
        val response = userService.registerUser(registerRequest)
        return ResponseEntity.status(response.status).body(response)
    }

    @PostMapping("/login")
    fun loginUser(
        @RequestBody @Valid loginRequest: LoginRequest
    ): ResponseEntity<Response> {
        val response = userService.loginUser(loginRequest)
        return ResponseEntity.status(response.status).body(response)
    }
}
