package com.tlz.stackarc.services.impl

import com.tlz.stackarc.dtos.ProductDto
import com.tlz.stackarc.dtos.Response
import com.tlz.stackarc.exceptions.NotFoundException
import com.tlz.stackarc.models.Product
import com.tlz.stackarc.repositories.CategoryRepository
import com.tlz.stackarc.repositories.ProductRepository
import com.tlz.stackarc.repositories.SupplierRepository
import com.tlz.stackarc.services.ProductService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val supplierRepository: SupplierRepository
) : ProductService {

    override fun addProduct(
        name: String,
        sku: String,
        price: BigDecimal,
        stockQuantity: Int,
        categoryId: Long,
        description: String,
        imageFile: MultipartFile?
    ): Response {
        val category = categoryRepository.findById(categoryId)
            .orElseThrow { NotFoundException("Category not found with ID: $categoryId") }

        val product = Product(
            name = name,
            sku = sku,
            price = price,
            stockQuantity = stockQuantity,
            description = description,
            imageUrl = imageFile?.originalFilename ?: "",
            category = category,
            createdAt = LocalDateTime.now()
        )

        productRepository.save(product)

        return Response(
            status = 201,
            message = "Product created successfully"
        )
    }

    override fun updateProduct(
        id: Long,
        name: String,
        sku: String,
        price: BigDecimal,
        stockQuantity: Int,
        categoryId: Long,
        description: String,
        imageFile: MultipartFile?
    ): Response {
        val existingProduct = productRepository.findById(id)
            .orElseThrow { NotFoundException("Product not found with ID: $id") }

        val category = categoryRepository.findById(categoryId)
            .orElseThrow { NotFoundException("Category not found with ID: $categoryId") }

        existingProduct.name = name
        existingProduct.sku = sku
        existingProduct.price = price
        existingProduct.stockQuantity = stockQuantity
        existingProduct.description = description
        existingProduct.imageUrl = imageFile?.originalFilename ?: existingProduct.imageUrl
        existingProduct.category = category

        productRepository.save(existingProduct)

        return Response(
            status = 200,
            message = "Product updated successfully"
        )
    }

    override fun getAllProducts(): Response {
        val products = productRepository.findAll().map { it.toDto() }
        return Response(
            status = 200,
            message = "Product list retrieved successfully",
            products = products
        )
    }

    override fun getProductById(id: Long): Response {
        val product = productRepository.findById(id)
            .orElseThrow { NotFoundException("Product not found with ID: $id") }

        return Response(
            status = 200,
            message = "Product retrieved successfully",
            product = product.toDto()
        )
    }

    override fun deleteProduct(id: Long): Response {
        val product = productRepository.findById(id)
            .orElseThrow { NotFoundException("Product not found with ID: $id") }

        productRepository.delete(product)

        return Response(
            status = 200,
            message = "Product deleted successfully"
        )
    }

    override fun searchProducts(keyword: String): Response {
        val foundProducts = productRepository.findByNameContainingOrDescriptionContaining(keyword, keyword)
            .map { it.toDto() }

        return if (foundProducts.isEmpty()) {
            Response(
                status = 404,
                message = "No products found matching '$keyword'"
            )
        } else {
            Response(
                status = 200,
                message = "Search successful",
                products = foundProducts
            )
        }
    }


    // === Mapping Extension ===
    private fun Product.toDto(): ProductDto = ProductDto(
        id = this.id,
        name = this.name,
        sku = this.sku,
        price = this.price,
        stockQuantity = this.stockQuantity,
        description = this.description,
        expiryDate = this.expiryDate,
        imageUrl = this.imageUrl,
        createdAt = this.createdAt,
        categoryId = this.category?.id ?: 0L,
        supplierId = this.supplier?.id ?: 0L,
        productId = this.id
    )
}
