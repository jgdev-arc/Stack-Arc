package com.tlz.Stack_Arc.dtos

import com.fasterxml.jackson.annotation.JsonInclude
import com.tlz.Stack_Arc.enums.UserRole
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Response(
    val status: Int,
    val message: String,
    val token: String? = null,
    val role: UserRole? = null,
    val expirationTime: String? = null,
    val totalPages: Int? = null,
    val totalElements: Long? = null,
    val user: UserDto? = null,
    val users: List<UserDto>? = null,
    val supplier: SupplierDto? = null,
    val suppliers: List<SupplierDto>? = null,
    val category: CategoryDto? = null,
    val categories: List<CategoryDto>? = null,
    val product: ProductDto? = null,
    val products: List<ProductDto>? = null,
    val transaction: TransactionDto? = null,
    val transactions: List<TransactionDto>? = null,
    val timestamp: LocalDateTime = LocalDateTime.now()
)
