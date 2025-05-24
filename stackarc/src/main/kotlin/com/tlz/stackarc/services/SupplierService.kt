package com.tlz.stackarc.services

import com.tlz.stackarc.dtos.Response
import com.tlz.stackarc.dtos.SupplierDto
import jakarta.validation.Valid

interface SupplierService {

    fun addSupplier(@Valid supplierDto: SupplierDto): Response

    fun getAllSuppliers(): Response

    fun getSupplierById(id: Long): Response

    fun updateSupplier(id: Long, @Valid supplierDto: SupplierDto): Response

    fun deleteSupplier(id: Long): Response
}
