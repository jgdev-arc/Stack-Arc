package com.tlz.stackarc.controllers

import com.tlz.stackarc.dtos.Response
import com.tlz.stackarc.enums.UserRole
import com.tlz.stackarc.services.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

data class RoleUpdateRequest(val role: UserRole)

@RestController
@RequestMapping("/api/users")
class AdminUsersController(private val userService: UserService) {

    @PutMapping("/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateRole(
        @PathVariable userId: Long,
        @RequestBody req: RoleUpdateRequest
    ): Response = userService.updateUserRole(userId, req.role)
}
