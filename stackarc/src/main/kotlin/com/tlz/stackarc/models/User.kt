package com.tlz.stackarc.models

import com.tlz.stackarc.enums.UserRole
import com.tlz.stackarc.models.Transaction
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

@Entity
@Table(name = "`user`")
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
    var role: com.tlz.stackarc.enums.UserRole = com.tlz.stackarc.enums.UserRole.USER,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    var transactions: MutableList<com.tlz.stackarc.models.Transaction> = mutableListOf(),

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()


) {
    override fun toString(): String {
        return "User(id=$id, " +
                "name='$name', " +
                "email='$email', " +
                "phoneNumber='$phoneNumber', " +
                "role=$role, " +
                "createdAt=$createdAt)"
    }
}
