package com.tlz.stackarc.controllers

import com.tlz.stackarc.dtos.ProductDto
import com.tlz.stackarc.dtos.Response
import com.tlz.stackarc.services.ProductService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.math.BigDecimal

@RestController
@RequestMapping("/api/products")
class ProductController(
    private val productService: ProductService
) {

    @PostMapping("/add", consumes = ["multipart/form-data"])
    @PreAuthorize("hasAuthority('ADMIN')")
    fun addProduct(
        @RequestParam name: String,
        @RequestParam sku: String,
        @RequestParam price: BigDecimal,
        @RequestParam stockQuantity: Int,
        @RequestParam categoryId: Long,
        @RequestParam description: String,
        @RequestParam(required = false) imageFile: MultipartFile?
    ): ResponseEntity<Response> {
        val response = productService.addProduct(
            name = name,
            sku = sku,
            price = price,
            stockQuantity = stockQuantity,
            categoryId = categoryId,
            description = description,
            imageFile = imageFile
        )
        return ResponseEntity.status(response.status).body(response)
    }


    @PostMapping("/update/{id}", consumes = ["multipart/form-data"])
    @PreAuthorize("hasAuthority('ADMIN')")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestParam name: String,
        @RequestParam sku: String,
        @RequestParam price: BigDecimal,
        @RequestParam stockQuantity: Int,
        @RequestParam categoryId: Long,
        @RequestParam description: String,
        @RequestParam(required = false) imageFile: MultipartFile?
    ): ResponseEntity<Response> {
        val response = productService.updateProduct(
            id, name, sku, price, stockQuantity, categoryId, description, imageFile
        )
        return ResponseEntity.status(response.status).body(response)
    }


    @GetMapping("/all")
    fun getAllProducts(): ResponseEntity<Response> {
        val response = productService.getAllProducts()
        return ResponseEntity.status(response.status).body(response)
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ResponseEntity<Response> {
        val response = productService.getProductById(id)
        return ResponseEntity.status(response.status).body(response)
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Response> {
        val response = productService.deleteProduct(id)
        return ResponseEntity.status(response.status).body(response)
    }

    @GetMapping("/search")
    fun searchProducts(@RequestParam input: String): ResponseEntity<Response> {
        val response = productService.searchProducts(input)
        return ResponseEntity.status(response.status).body(response)
    }

}
