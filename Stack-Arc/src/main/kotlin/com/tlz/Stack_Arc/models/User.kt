package com.tlz.Stack_Arc.models

import com.tlz.Stack_Arc.enums.UserRole
import com.tlz.Stack_Arc.models.Transaction
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

@Entity
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @field:NotBlank(message = "Name is required")
    var name: String = "",

    @Column(unique = true)
    @field:NotBlank(message = "Email is required")
    var email: String = "",

    @field:NotBlank(message = "Password is required")
    var password: String = "",

    @field:NotBlank(message = "Phone number is required")
    var phoneNumber: String = "",

    @Enumerated(EnumType.STRING)
    var role: UserRole = UserRole.USER,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var transactions: MutableList<Transaction> = mutableListOf(),

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()


) {
    override fun toString(): String {
        return "User(id=$id, " +
                "name='$name', " +
                "email='$email', " +
                "password='$password', " +
                "phoneNumber='$phoneNumber', " +
                "role=$role, " +
                "createdAt=$createdAt)"
    }
}
