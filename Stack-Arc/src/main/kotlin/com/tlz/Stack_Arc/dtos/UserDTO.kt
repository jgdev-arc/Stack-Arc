package com.tlz.Stack_Arc.dtos

import com.tlz.Stack_Arc.enums.UserRole
import java.time.LocalDateTime

data class UserDto(
    val id: Long,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val role: UserRole,
    val transaction: List<TransactionDto>,
    val createdAt: LocalDateTime
)
