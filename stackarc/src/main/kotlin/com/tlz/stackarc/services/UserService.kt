package com.tlz.stackarc.services

import com.tlz.stackarc.dtos.*
import org.springframework.stereotype.Service

@Service
interface UserService {

    fun registerUser(registerRequest: RegisterRequest): Response

    fun loginUser(loginRequest: LoginRequest): Response

    fun getAllUsers(): Response

    fun getCurrentLoggedInUser(): Response

    fun getUserById(id: Long): Response

    fun updateUser(id: Long, userDto: UserDto): Response

    fun deleteUser(id: Long): Response

    fun getUserTransactions(id: Long): Response

    fun changePassword(request: ChangePasswordRequest): Response
}
