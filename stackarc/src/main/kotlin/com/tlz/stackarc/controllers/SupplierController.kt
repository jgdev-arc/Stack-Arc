package com.tlz.stackarc.controllers

import com.tlz.stackarc.dtos.Response
import com.tlz.stackarc.dtos.SupplierDto
import com.tlz.stackarc.services.SupplierService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/suppliers")
class SupplierController(
    private val supplierService: SupplierService
) {

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    fun addSupplier(@RequestBody @Valid supplierDto: SupplierDto): ResponseEntity<Response> {
        val response = supplierService.addSupplier(supplierDto)
        return ResponseEntity.status(response.status).body(response)
    }

    @GetMapping
    fun getAllSuppliers(): ResponseEntity<Response> {
        val response = supplierService.getAllSuppliers()
        return ResponseEntity.status(response.status).body(response)
    }

    @GetMapping("/{id}")
    fun getSupplierById(@PathVariable id: Long): ResponseEntity<Response> {
        val response = supplierService.getSupplierById(id)
        return ResponseEntity.status(response.status).body(response)
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun updateSupplier(
        @PathVariable id: Long,
        @RequestBody @Valid supplierDto: SupplierDto
    ): ResponseEntity<Response> {
        val response = supplierService.updateSupplier(id, supplierDto)
        return ResponseEntity.status(response.status).body(response)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun deleteSupplier(@PathVariable id: Long): ResponseEntity<Response> {
        val response = supplierService.deleteSupplier(id)
        return ResponseEntity.status(response.status).body(response)
    }
}
