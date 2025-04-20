package com.tlz.stackarc.services.impl

import com.tlz.stackarc.dtos.CategoryDto
import com.tlz.stackarc.dtos.Response
import com.tlz.stackarc.exceptions.NotFoundException
import com.tlz.stackarc.models.Category
import com.tlz.stackarc.repositories.CategoryRepository
import com.tlz.stackarc.services.CategoryService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository
) : CategoryService {

    override fun createCategory(categoryDto: CategoryDto): Response {
        val category = Category(name = categoryDto.name)
        val savedCategory = categoryRepository.save(category)

        return Response(
            status = 201,
            message = "Category created successfully",
            category = savedCategory.toDto()
        )
    }

    override fun getAllCategories(): Response {
        val categories = categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
        val categoryDtos = categories.map { it.toDto() }

        return Response(
            status = 200,
            message = "Categories retrieved successfully",
            categories = categoryDtos
        )
    }

    override fun getCategoryById(id: Long): Response {
        val category = categoryRepository.findById(id)
            .orElseThrow { NotFoundException("Category not found with ID: $id") }

        return Response(
            status = 200,
            message = "Category retrieved successfully",
            category = category.toDto()
        )
    }

    override fun updateCategory(id: Long, categoryDto: CategoryDto): Response {
        val category = categoryRepository.findById(id)
            .orElseThrow { NotFoundException("Category not found with ID: $id") }

        category.name = categoryDto.name
        categoryRepository.save(category)

        return Response(
            status = 200,
            message = "Category updated successfully",
            category = category.toDto()
        )
    }

    override fun deleteCategory(id: Long): Response {
        categoryRepository.findById(id)
            .orElseThrow { NotFoundException("Category not found with ID: $id") }

        categoryRepository.deleteById(id)

        return Response(
            status = 200,
            message = "Category deleted successfully"
        )
    }


    private fun Category.toDto(): CategoryDto = CategoryDto(
        id = this.id,
        name = this.name
    )
}
