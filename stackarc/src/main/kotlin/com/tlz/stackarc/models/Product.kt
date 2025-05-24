package com.tlz.stackarc.models

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PositiveOrZero
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
class Product(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotBlank(message = "Product name is required")
    var name: String = "",

    @field:NotBlank(message = "SKU is required")
    var sku: String = "",

    @Column(precision = 10, scale = 2)
    @field:PositiveOrZero(message = "Product price must be zero or positive")
    var price: BigDecimal = BigDecimal.ZERO,

    @field:PositiveOrZero(message = "Stock quantity must be zero or positive")
    var stockQuantity: Int = 0,

    var description: String = "",

    var expiryDate: LocalDate? = null,

    var imageUrl: String = "",

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    var category: com.tlz.stackarc.models.Category? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    var supplier: Supplier? = null


) {
    override fun toString(): String {
        return "Product(id=$id, " +
                "name='$name', " +
                "sku='$sku', " +
                "price=$price, " +
                "stockQuantity=$stockQuantity, " +
                "description='$description', " +
                "expiryDate=$expiryDate, " +
                "imageUrl='$imageUrl', " +
                "createdAt=$createdAt)"
    }
}
