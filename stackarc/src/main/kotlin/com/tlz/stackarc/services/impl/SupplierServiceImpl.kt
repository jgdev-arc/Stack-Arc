package com.tlz.stackarc.services.impl

import com.tlz.stackarc.dtos.Response
import com.tlz.stackarc.dtos.SupplierDto
import com.tlz.stackarc.exceptions.NotFoundException
import com.tlz.stackarc.models.Supplier
import com.tlz.stackarc.repositories.SupplierRepository
import com.tlz.stackarc.services.SupplierService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class SupplierServiceImpl(
    private val supplierRepository: SupplierRepository
) : SupplierService {

    override fun addSupplier(supplierDto: SupplierDto): Response {
        val supplier = Supplier(
            name = supplierDto.name,
            contactInfo = supplierDto.contactInfo,
            address = supplierDto.address
        )

        val saved = supplierRepository.save(supplier)

        return Response(
            status = 201,
            message = "Supplier created successfully",
            supplier = saved.toDto()
        )
    }

    override fun getAllSuppliers(): Response {
        val suppliers = supplierRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
        val supplierDtos = suppliers.map { it.toDto() }

        return Response(
            status = 200,
            message = "Suppliers retrieved successfully",
            suppliers = supplierDtos
        )
    }

    override fun getSupplierById(id: Long): Response {
        val supplier = supplierRepository.findById(id)
            .orElseThrow { NotFoundException("Supplier not found with ID: $id") }

        return Response(
            status = 200,
            message = "Supplier retrieved successfully",
            supplier = supplier.toDto()
        )
    }

    override fun updateSupplier(id: Long, supplierDto: SupplierDto): Response {
        val supplier = supplierRepository.findById(id)
            .orElseThrow { NotFoundException("Supplier not found with ID: $id") }

        supplier.name = supplierDto.name
        supplier.contactInfo = supplierDto.contactInfo
        supplier.address = supplierDto.address

        supplierRepository.save(supplier)

        return Response(
            status = 200,
            message = "Supplier updated successfully",
            supplier = supplier.toDto()
        )
    }

    override fun deleteSupplier(id: Long): Response {
        supplierRepository.findById(id)
            .orElseThrow { NotFoundException("Supplier not found with ID: $id") }

        supplierRepository.deleteById(id)

        return Response(
            status = 200,
            message = "Supplier deleted successfully"
        )
    }


    private fun Supplier.toDto(): SupplierDto = SupplierDto(
        id = this.id,
        name = this.name,
        contactInfo = this.contactInfo,
        address = this.address
    )
}
