package com.tlz.stackarc.services

import com.tlz.stackarc.dtos.CategoryDto
import com.tlz.stackarc.dtos.Response
import jakarta.validation.Valid

interface CategoryService {

    fun createCategory(@Valid categoryDto: CategoryDto): Response

    fun getAllCategories(): Response

    fun getCategoryById(id: Long): Response

    fun updateCategory(id: Long, @Valid categoryDto: CategoryDto): Response

    fun deleteCategory(id: Long): Response
}
