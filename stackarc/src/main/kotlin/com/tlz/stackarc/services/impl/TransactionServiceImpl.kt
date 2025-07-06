package com.tlz.stackarc.services.impl

import com.tlz.stackarc.dtos.TransactionDto
import com.tlz.stackarc.dtos.Response
import com.tlz.stackarc.dtos.TransactionRequest
import com.tlz.stackarc.enums.TransactionStatus
import com.tlz.stackarc.enums.TransactionType
import com.tlz.stackarc.exceptions.NotFoundException
import com.tlz.stackarc.models.Transaction
import com.tlz.stackarc.repositories.ProductRepository
import com.tlz.stackarc.repositories.SupplierRepository
import com.tlz.stackarc.repositories.TransactionRepository
import com.tlz.stackarc.repositories.UserRepository
import com.tlz.stackarc.services.TransactionService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TransactionServiceImpl(
    private val transactionRepository: TransactionRepository,
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository,
    private val supplierRepository: SupplierRepository
) : TransactionService {

    override fun addTransaction(transactionDto: TransactionDto): Response {
        val request = TransactionRequest(
            productId = transactionDto.product ?: 0L,
            quantityId = transactionDto.totalProducts,
            supplierId = transactionDto.supplier ?: 0L,
            description = transactionDto.description,
            note = transactionDto.note
        )
        return purchase(request)
    }



    override fun getAllTransactions(): Response {
        val transactions = transactionRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
            .map { it.toDto() }

        return Response(
            status = 200,
            message = "Transactions retrieved successfully",
            transactions = transactions
        )
    }

    override fun getTransactionById(id: Long): Response {
        val tx = transactionRepository.findById(id)
            .orElseThrow { NotFoundException("Transaction not found with ID: $id") }

        return Response(
            status = 200,
            message = "Transaction found",
            transaction = tx.toDto()
        )
    }

    override fun updateTransaction(id: Long, transactionDto: TransactionDto): Response {
        val existing = transactionRepository.findById(id)
            .orElseThrow { NotFoundException("Transaction not found with ID: $id") }

        existing.totalProducts = transactionDto.totalProducts
        existing.totalPrice = transactionDto.totalPrice
        existing.type = transactionDto.type
        existing.status = transactionDto.status
        existing.description = transactionDto.description
        existing.note = transactionDto.note
        existing.updatedAt = LocalDateTime.now()
        transactionDto.product?.let {
            existing.product = productRepository.findById(it)
                .orElseThrow { NotFoundException("Product not found with ID: $it") }
        }
        transactionDto.user?.let {
            existing.user = userRepository.findById(it)
                .orElseThrow { NotFoundException("User not found with ID: $it") }
        }
        transactionDto.supplier?.let {
            existing.supplier = supplierRepository.findById(it)
                .orElseThrow { NotFoundException("Supplier not found with ID: $it") }
        }

        transactionRepository.save(existing)
        return Response(status = 200, message = "Transaction updated")
    }

    override fun deleteTransaction(id: Long): Response {
        if (!transactionRepository.existsById(id)) {
            throw NotFoundException("Transaction not found with ID: $id")
        }
        transactionRepository.deleteById(id)
        return Response(status = 200, message = "Transaction deleted")
    }

    override fun searchTransactions(input: String): Response {
        val found = transactionRepository.findByDescriptionContainingOrNoteContaining(
            input, input
        ).map { it.toDto() }

        return if (found.isEmpty())
            Response(status = 404, message = "No matching transactions found")
        else
            Response(status = 200, message = "Search successful", transactions = found)
    }

    override fun purchase(request: TransactionRequest): Response {
        val dto = TransactionDto(
            id = 0L,
            totalProducts = request.quantityId,
            totalPrice = productRepository.findById(request.productId)
                .orElseThrow { NotFoundException("Product not found") }
                .price.multiply(request.quantityId.toBigDecimal()),
            type = TransactionType.PURCHASE,
            status = TransactionStatus.PENDING,
            description = request.description,
            note = request.note,
            updatedAt = LocalDateTime.now(),
            createdAt = LocalDateTime.now(),
            product = request.productId,
            user = 1L,
            supplier = request.supplierId
        )

        return createTransaction(dto, TransactionType.PURCHASE)
    }


    override fun sell(request: TransactionRequest): Response {
        val product = productRepository.findById(request.productId)
            .orElseThrow { NotFoundException("Product not found") }

        val dto = TransactionDto(
            id = 0L,
            totalProducts = request.quantityId,
            totalPrice = product.price.multiply(request.quantityId.toBigDecimal()),
            type = TransactionType.SALE,
            status = TransactionStatus.PENDING,
            description = request.description,
            note = request.note,
            updatedAt = LocalDateTime.now(),
            createdAt = LocalDateTime.now(),
            product = request.productId,
            user = 1L,
            supplier = request.supplierId
        )

        return createTransaction(dto, TransactionType.SALE)
    }


    override fun returnToSupplier(request: TransactionRequest): Response {
        val product = productRepository.findById(request.productId)
            .orElseThrow { NotFoundException("Product not found") }

        val dto = TransactionDto(
            id = 0L,
            totalProducts = request.quantityId,
            totalPrice = product.price.multiply(request.quantityId.toBigDecimal()),
            type = TransactionType.RETURN_TO_SUPPLIER,
            status = TransactionStatus.PENDING,
            description = request.description,
            note = request.note,
            updatedAt = LocalDateTime.now(),
            createdAt = LocalDateTime.now(),
            product = request.productId,
            user = 1L,
            supplier = request.supplierId
        )

        return createTransaction(dto, TransactionType.RETURN_TO_SUPPLIER)
    }


    override fun getAllTransactionByMonthAndYear(month: Int, year: Int): Response {
        val list = transactionRepository.findAllByMonthAndYear(month, year)
            .map { it.toDto() }

        return Response(
            status = 200,
            message = "Transactions for $month/$year",
            transactions = list
        )
    }


    private fun createTransaction(dto: TransactionDto, type: TransactionType): Response {
        val product = productRepository.findById(dto.product
            ?: throw NotFoundException("Product ID required")).orElseThrow {
            NotFoundException("Product not found with ID: ${dto.product}")
        }

        val user = userRepository.findById(dto.user
            ?: throw NotFoundException("User ID required")).orElseThrow {
            NotFoundException("User not found with ID: ${dto.user}")
        }

        val supplier = supplierRepository.findById(dto.supplier
            ?: throw NotFoundException("Supplier ID required")).orElseThrow {
            NotFoundException("Supplier not found with ID: ${dto.supplier}")
        }

        val now = LocalDateTime.now()
        val tx = Transaction(
            totalProducts = dto.totalProducts,
            totalPrice = dto.totalPrice,
            type = type,
            status = TransactionStatus.PENDING,
            description = dto.description,
            note = dto.note,
            createdAt = now,
            updatedAt = now,
            product = product,
            user = user,
            supplier = supplier
        )

        transactionRepository.save(tx)
        return Response(status = 201, message = "${type.name} transaction created")
    }


    private fun Transaction.toDto(): TransactionDto = TransactionDto(
        id = id,
        totalProducts = totalProducts,
        totalPrice = totalPrice,
        type = type,
        status = status,
        description = description,
        note = note,
        updatedAt = updatedAt,
        createdAt = createdAt,
        product = product?.id,
        user = user?.id,
        supplier = supplier?.id
    )
}
