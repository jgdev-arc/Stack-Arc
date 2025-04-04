package com.tlz.stackarc.dtos

import com.tlz.stackarc.enums.TransactionStatus
import com.tlz.stackarc.enums.TransactionType
import java.math.BigDecimal
import java.time.LocalDateTime

data class TransactionDto(
    val id: Long,
    val totalProducts: Int,
    val totalPrice: BigDecimal,
    val type: com.tlz.stackarc.enums.TransactionType,
    val status: com.tlz.stackarc.enums.TransactionStatus,
    val description: String,
    val note: String,
    val updatedAt: LocalDateTime,
    val createdAt: LocalDateTime,
    val product: Long?,
    val user: Long?,
    val supplier: Long?
)
