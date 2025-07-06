package com.tlz.stackarc.utils

import com.tlz.stackarc.dtos.TransactionRequest
import com.tlz.stackarc.enums.TransactionStatus
import com.tlz.stackarc.enums.TransactionType
import com.tlz.stackarc.models.Transaction
import java.math.BigDecimal

fun TransactionRequest.toEntity(): Transaction {
    return Transaction(
        totalProducts = this.quantityId,
        totalPrice = BigDecimal.ZERO, // To be calculated later
        type = TransactionType.PURCHASE, // Default or determined based on context
        status = TransactionStatus.PENDING, // Default status
        description = this.description,
        note = this.note
        // product, user, and supplier should be set later in the service
    )
}

