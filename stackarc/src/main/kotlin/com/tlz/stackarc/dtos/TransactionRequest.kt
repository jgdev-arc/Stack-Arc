package com.tlz.stackarc.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

data class TransactionRequest(
    @field:Positive(message = "ProductId is required")
    val productId: Long,

    @field:Positive(message = "QuantityId is required")
    val quantityId: Int,

    @field:Positive(message = "SupplierId is required")
    val supplierId: Long,

    val description: String,

    val note: String
)
