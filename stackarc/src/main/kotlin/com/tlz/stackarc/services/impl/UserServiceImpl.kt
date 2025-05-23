package com.tlz.stackarc.services.impl

import com.tlz.stackarc.security.JwtUtils
import com.tlz.stackarc.dtos.*
import com.tlz.stackarc.enums.UserRole
import com.tlz.stackarc.exceptions.InvalidCredentialsException
import com.tlz.stackarc.exceptions.NotFoundException
import com.tlz.stackarc.models.User
import com.tlz.stackarc.repositories.UserRepository
import com.tlz.stackarc.services.UserService
import org.springframework.data.domain.Sort
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtils: JwtUtils
) : UserService {

    override fun registerUser(registerRequest: RegisterRequest): Response {
        val role = registerRequest.role ?: UserRole.MANAGER

        val userToSave = User(
            name = registerRequest.name,
            email = registerRequest.email,
            password = passwordEncoder.encode(registerRequest.password),
            role = role,
            phoneNumber = "N/A", // adjust if needed
            createdAt = LocalDateTime.now()
        )

        userRepository.save(userToSave)

        return Response(
            status = 200,
            message = "Registration Successful!"
        )
    }

    override fun loginUser(loginRequest: LoginRequest): Response {
        val user = userRepository.findByEmail(loginRequest.email)
            .orElseThrow { NotFoundException("Email Not Found") }

        if (!passwordEncoder.matches(loginRequest.password, user.password)) {
            throw InvalidCredentialsException("Password does not match")
        }

        val token = jwtUtils.generateToken(user.email)

        return Response(
            status = 200,
            message = "User logged in successfully!",
            token = token,
            role = user.role,
            expirationTime = "6 months",
            user = user.toDto()
        )
    }

    override fun getAllUsers(): Response {
        val users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
        val userDtos = users.map { it.toDto() }

        return Response(
            status = 200,
            message = "Success",
            users = userDtos
        )
    }

    override fun getCurrentLoggedInUser(): Response {
        val email = SecurityContextHolder.getContext().authentication.name
        val user = userRepository.findByEmail(email)
            .orElseThrow { NotFoundException("User Not Found") }

        return Response(
            status = 200,
            message = "Success",
            user = user.toDto()
        )
    }

    override fun getUserById(id: Long): Response {
        val user = userRepository.findById(id)
            .orElseThrow { NotFoundException("User Not Found") }

        return Response(
            status = 200,
            message = "Success",
            user = user.toDto()
        )
    }

    override fun changePassword(request: ChangePasswordRequest): Response {
        val email = SecurityContextHolder.getContext().authentication.name
        val user = userRepository.findByEmail(email)
            .orElseThrow { NotFoundException("User not found") }

        if (!passwordEncoder.matches(request.currentPassword, user.password)) {
            throw InvalidCredentialsException("Current password is incorrect")
        }

        user.password = passwordEncoder.encode(request.newPassword)
        userRepository.save(user)

        return Response(
            status = 200,
            message = "Password changed successfully"
        )
    }


    override fun updateUser(id: Long, userDto: UserDto): Response {
        val existingUser = userRepository.findById(id)
            .orElseThrow { NotFoundException("User Not Found") }

        userDto.email?.let { existingUser.email = it }
        userDto.phoneNumber?.let { existingUser.phoneNumber = it }
        userDto.name?.let { existingUser.name = it }
        userDto.role?.let { existingUser.role = it }

        userRepository.save(existingUser)

        return Response(
            status = 200,
            message = "User successfully updated"
        )
    }

    override fun deleteUser(id: Long): Response {
        userRepository.findById(id)
            .orElseThrow { NotFoundException("User Not Found") }

        userRepository.deleteById(id)

        return Response(
            status = 200,
            message = "User successfully deleted"
        )
    }

    override fun getUserTransactions(id: Long): Response {
        val user = userRepository.findById(id)
            .orElseThrow { NotFoundException("User Not Found") }

        val transactions = user.transactions.map { it.toDto() }

        return Response(
            status = 200,
            message = "User transactions retrieved",
            transactions = transactions
        )
    }


    // === Mapping Extensions ===

    private fun User.toDto(): UserDto = UserDto(
        id = this.id,
        name = this.name,
        email = this.email,
        phoneNumber = this.phoneNumber,
        role = this.role,
        createdAt = this.createdAt,
        transactions = null // or an empty list if preferred
    )


    private fun com.tlz.stackarc.models.Transaction.toDto(): TransactionDto = TransactionDto(
        id = this.id,
        totalProducts = this.totalProducts,
        totalPrice = this.totalPrice,
        type = this.type,
        status = this.status,
        description = this.description,
        note = this.note,
        updatedAt = this.updatedAt,
        createdAt = this.createdAt,
        product = this.product?.id,
        user = this.user?.id,
        supplier = this.supplier?.id
    )

}
