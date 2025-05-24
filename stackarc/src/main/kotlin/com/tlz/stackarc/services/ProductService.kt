package com.tlz.stackarc.services

import com.tlz.stackarc.dtos.ProductDto
import com.tlz.stackarc.dtos.Response
import jakarta.validation.Valid
import org.springframework.web.multipart.MultipartFile
import java.math.BigDecimal

interface ProductService {

    fun addProduct(
        name: String,
        sku: String,
        price: BigDecimal,
        stockQuantity: Int,
        categoryId: Long,
        description: String,
        imageFile: MultipartFile?
    ): Response


    fun getAllProducts(): Response

    fun getProductById(id: Long): Response

    fun updateProduct(
        id: Long,
        name: String,
        sku: String,
        price: BigDecimal,
        stockQuantity: Int,
        categoryId: Long,
        description: String,
        imageFile: MultipartFile?
    ): Response


    fun deleteProduct(id: Long): Response

    fun searchProducts(keyword: String): Response
}

