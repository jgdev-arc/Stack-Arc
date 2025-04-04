package com.tlz.stackarc.security

import com.tlz.stackarc.repositories.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: com.tlz.stackarc.repositories.UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username)
            .orElseThrow {
                UsernameNotFoundException("User with email $username not found")
            }

        return AuthUser(user)
    }


}
