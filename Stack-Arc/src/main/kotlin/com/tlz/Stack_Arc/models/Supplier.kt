package com.tlz.Stack_Arc.models

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
class Supplier(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotBlank(message = "Supplier name is required")
    var name: String = "",

    @field:NotBlank(message = "Contact info is required")
    var contactInfo: String = "",

    @field:NotBlank(message = "Address is required")
    var address: String = ""

) {
    override fun toString(): String {
        return "Supplier(id=$id, name='$name', contactInfo='$contactInfo', address='$address')"
    }
}
