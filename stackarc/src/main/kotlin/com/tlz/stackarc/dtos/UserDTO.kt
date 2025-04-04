package com.tlz.stackarc.dtos

import com.tlz.stackarc.enums.UserRole
import java.time.LocalDateTime

data class UserDto(
    val id: Long,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val role: com.tlz.stackarc.enums.UserRole,
    val transaction: List<com.tlz.stackarc.dtos.TransactionDto>,
    val createdAt: LocalDateTime
)
