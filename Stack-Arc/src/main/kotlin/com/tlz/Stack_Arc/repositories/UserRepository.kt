package com.tlz.Stack_Arc.repositories

import com.tlz.Stack_Arc.models.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>
}
