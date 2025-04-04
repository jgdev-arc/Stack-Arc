package com.tlz.stackarc.dtos

import com.fasterxml.jackson.annotation.JsonInclude
import com.tlz.stackarc.enums.UserRole
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Response(
    val status: Int,
    val message: String,
    val token: String? = null,
    val role: com.tlz.stackarc.enums.UserRole? = null,
    val expirationTime: String? = null,
    val totalPages: Int? = null,
    val totalElements: Long? = null,
    val user: com.tlz.stackarc.dtos.UserDto? = null,
    val users: List<com.tlz.stackarc.dtos.UserDto>? = null,
    val supplier: com.tlz.stackarc.dtos.SupplierDto? = null,
    val suppliers: List<com.tlz.stackarc.dtos.SupplierDto>? = null,
    val category: com.tlz.stackarc.dtos.CategoryDto? = null,
    val categories: List<com.tlz.stackarc.dtos.CategoryDto>? = null,
    val product: com.tlz.stackarc.dtos.ProductDto? = null,
    val products: List<com.tlz.stackarc.dtos.ProductDto>? = null,
    val transaction: com.tlz.stackarc.dtos.TransactionDto? = null,
    val transactions: List<com.tlz.stackarc.dtos.TransactionDto>? = null,
    val timestamp: LocalDateTime = LocalDateTime.now()
)
