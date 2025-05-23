package com.tlz.stackarc.controllers

import com.tlz.stackarc.dtos.*
import com.tlz.stackarc.services.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun getAllUsers(): ResponseEntity<Response> {
        val response = userService.getAllUsers()
        return ResponseEntity.status(response.status).body(response)
    }

    @GetMapping("/{id}")
    fun getCurrentLoggedInUser(): ResponseEntity<Response> {
        val response = userService.getCurrentLoggedInUser()
        return ResponseEntity.status(response.status).body(response)
    }

    @PutMapping("/update/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @RequestBody @Valid userDto: UserDto
    ): ResponseEntity<Response> {
        val response = userService.updateUser(id, userDto)
        return ResponseEntity.status(response.status).body(response)
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Response> {
        val response = userService.deleteUser(id)
        return ResponseEntity.status(response.status).body(response)
    }

    @GetMapping("/transactions/{id}")
    fun getUserTransactions(@PathVariable id: Long): ResponseEntity<Response> {
        val response = userService.getUserTransactions(id)
        return ResponseEntity.status(response.status).body(response)
    }

}
