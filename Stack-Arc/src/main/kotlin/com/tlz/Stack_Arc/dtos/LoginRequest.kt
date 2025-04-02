package com.tlz.Stack_Arc.dtos

import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank(message = "Email is required")
    val email: String,

    @field:NotBlank(message = "Password is required")
    val password: String
)
