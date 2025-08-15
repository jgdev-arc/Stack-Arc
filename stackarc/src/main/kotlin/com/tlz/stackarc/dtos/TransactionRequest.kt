package com.tlz.stackarc.dtos

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class ReturnToSupplierRequest(
    @field:NotNull @field:Positive(message = "productId must be > 0")
    val productId: Long,

    @field:NotNull @field:Positive(message = "quantity must be > 0")
    val quantity: Int,

    @field:NotNull @field:Positive(message = "supplierId must be > 0")
    val supplierId: Long,

    val description: String? = null
)
