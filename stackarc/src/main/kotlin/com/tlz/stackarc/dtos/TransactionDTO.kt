package com.tlz.stackarc.dtos

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.tlz.stackarc.enums.TransactionStatus
import com.tlz.stackarc.enums.TransactionType
import java.math.BigDecimal
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TransactionDto(
    val id: Long,
    val totalProducts: Int,
    val totalPrice: BigDecimal,
    val type: TransactionType,           // import the enum instead of FQN
    val status: TransactionStatus,       // import the enum
    val description: String? = null,

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val updatedAt: LocalDateTime,

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val createdAt: LocalDateTime,

    // clarify that these are IDs; keep nullable if not always present
    val productId: Long? = null,
    val userId: Long? = null,
    val supplierId: Long? = null
)
