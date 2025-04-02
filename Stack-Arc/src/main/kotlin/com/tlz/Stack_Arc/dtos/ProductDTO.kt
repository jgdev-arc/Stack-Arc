package com.tlz.Stack_Arc.dtos

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

data class ProductDto(
    val id: Long,
    val name: String,
    val sku: String,
    val supplierId: Long,
    val categoryId: Long,
    val productId: Long,
    val price: BigDecimal,
    val stockQuantity: Int,
    val description: String,
    val expiryDate: LocalDate?,
    val imageUrl: String,
    val createdAt: LocalDateTime

)
