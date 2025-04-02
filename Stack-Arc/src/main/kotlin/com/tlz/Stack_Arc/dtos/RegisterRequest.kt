package com.tlz.Stack_Arc.dtos

import com.tlz.Stack_Arc.enums.UserRole
import jakarta.validation.constraints.NotBlank

data class RegisterRequest(
    @field:NotBlank(message = "Name is required")
    val name: String,

    @field:NotBlank(message = "Email is required")
    val email: String,

    @field:NotBlank(message = "Password is required")
    val password: String,

    val role: UserRole
)
