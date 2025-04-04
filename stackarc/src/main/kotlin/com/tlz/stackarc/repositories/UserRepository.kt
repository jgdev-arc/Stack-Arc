package com.tlz.stackarc.repositories

import com.tlz.stackarc.models.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<com.tlz.stackarc.models.User, Long> {
    fun findByEmail(email: String): Optional<com.tlz.stackarc.models.User>
}
