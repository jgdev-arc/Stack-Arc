package com.tlz.Stack_Arc.models

import com.tlz.Stack_Arc.enums.TransactionStatus
import com.tlz.Stack_Arc.enums.TransactionType
import jakarta.persistence.*
import jakarta.validation.constraints.Min
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
class Transaction(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:Min(value = 1, message = "Total products must be at least 1")
    var totalProducts: Int = 1,

    @Column(precision = 10, scale = 2)
    var totalPrice: BigDecimal = BigDecimal.ZERO,

    @Enumerated(EnumType.STRING)
    var type: TransactionType = TransactionType.PURCHASE,

    @Enumerated(EnumType.STRING)
    var status: TransactionStatus = TransactionStatus.PENDING,

    var description: String = "",

    var note: String = "",

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "created_at", updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    var product: Product? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    var supplier: Supplier? = null

) {
    override fun toString(): String {
        return "Transaction(id=$id, " +
                "totalProducts=$totalProducts, " +
                "totalPrice=$totalPrice, " +
                "type=$type, " +
                "status=$status, " +
                "description='$description', " +
                "note='$note', " +
                "updatedAt=$updatedAt, " +
                "createdAt=$createdAt)"
    }
}
