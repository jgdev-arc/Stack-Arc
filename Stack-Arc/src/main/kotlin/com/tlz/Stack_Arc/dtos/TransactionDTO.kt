package com.tlz.Stack_Arc.dtos

import com.tlz.Stack_Arc.enums.TransactionStatus
import com.tlz.Stack_Arc.enums.TransactionType
import java.math.BigDecimal
import java.time.LocalDateTime

data class TransactionDto(
    val id: Long,
    val totalProducts: Int,
    val totalPrice: BigDecimal,
    val type: TransactionType,
    val status: TransactionStatus,
    val description: String,
    val note: String,
    val updatedAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val product: Long?,
    val user: Long?,
    val supplier: Long?
)
