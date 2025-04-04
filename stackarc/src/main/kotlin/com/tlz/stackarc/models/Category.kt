package com.tlz.stackarc.models

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
class Category(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotBlank(message = "Category name is required")
    var name: String = "",

    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var products: MutableList<com.tlz.stackarc.models.Product> = mutableListOf()

) {
    override fun toString(): String {
        return "Category(id=$id, name='$name')"
    }
}
