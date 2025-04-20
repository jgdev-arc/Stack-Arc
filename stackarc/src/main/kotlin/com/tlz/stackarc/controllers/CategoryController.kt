package com.tlz.stackarc.controllers

import com.tlz.stackarc.dtos.CategoryDto
import com.tlz.stackarc.dtos.Response
import com.tlz.stackarc.services.CategoryService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categories")
class CategoryController(
    private val categoryService: CategoryService
) {

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun createCategory(@RequestBody @Valid categoryDto: CategoryDto): ResponseEntity<Response> {
        val response = categoryService.createCategory(categoryDto)
        return ResponseEntity.status(response.status).body(response)
    }

    @GetMapping("/all")
    fun getAllCategories(): ResponseEntity<Response> {
        val response = categoryService.getAllCategories()
        return ResponseEntity.status(response.status).body(response)
    }

    @GetMapping("/{id}")
    fun getCategoryById(@PathVariable id: Long): ResponseEntity<Response> {
        val response = categoryService.getCategoryById(id)
        return ResponseEntity.status(response.status).body(response)
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun updateCategory(
        @PathVariable id: Long,
        @RequestBody @Valid categoryDto: CategoryDto
    ): ResponseEntity<Response> {
        val response = categoryService.updateCategory(id, categoryDto)
        return ResponseEntity.status(response.status).body(response)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun deleteCategory(@PathVariable id: Long): ResponseEntity<Response> {
        val response = categoryService.deleteCategory(id)
        return ResponseEntity.status(response.status).body(response)
    }
}
