package com.tlz.stackarc.dtos

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

data class PurchaseRequest(
    @field:Positive(message = "ProductId is required")
    val productId: Long,

    @field:Positive(message = "Quantity is required")
    val quantity: Int,

    @field:Positive(message = "SupplierId is required")
    val supplierId: Long,

    val description: String
)
