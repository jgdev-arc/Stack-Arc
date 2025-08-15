package com.tlz.stackarc.services.impl

import com.tlz.stackarc.dtos.*
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
        val productId = transactionDto.productId
            ?: throw NotFoundException("Product ID required")

        val supplierId = transactionDto.supplierId
            ?: throw NotFoundException("Supplier ID required for purchase")

        val request = PurchaseRequest(
            productId = productId,
            quantity = transactionDto.totalProducts,
            supplierId = supplierId,
            description = transactionDto.description ?: ""
        )
        return purchase(request)
    }

    override fun getAllTransactions(q: String): Response {
        val list = if (q.isBlank()) {
            transactionRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
        } else {
            transactionRepository.findByDescriptionContaining(q)
                .sortedByDescending { it.createdAt }
        }.map { it.toDto() }

        return Response(
            status = 200,
            message = "Transactions retrieved successfully",
            transactions = list
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

    override fun updateTransaction(id: Long, dto: TransactionDto): Response {
        val existing = transactionRepository.findById(id)
            .orElseThrow { NotFoundException("Transaction not found with ID: $id") }

        existing.totalProducts = dto.totalProducts
        existing.totalPrice = dto.totalPrice
        existing.type = dto.type
        existing.status = dto.status
        existing.description = dto.description ?: existing.description
        existing.updatedAt = LocalDateTime.now()

        dto.productId?.let { pid ->
            val product = productRepository.findById(pid)
                .orElseThrow { NotFoundException("Product not found with ID: $pid") }
            existing.product = product
        }

        dto.userId?.let { uid ->
            val user = userRepository.findById(uid)
                .orElseThrow { NotFoundException("User not found with ID: $uid") }
            existing.user = user
        }

        dto.supplierId?.let { sid ->
            val supplier = supplierRepository.findById(sid)
                .orElseThrow { NotFoundException("Supplier not found with ID: $sid") }
            existing.supplier = supplier
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
        val found = transactionRepository.findByDescriptionContaining(input)
            .map { it.toDto() }

        return if (found.isEmpty())
            Response(status = 404, message = "No matching transactions found")
        else
            Response(status = 200, message = "Search successful", transactions = found)
    }

    override fun purchase(request: PurchaseRequest): Response {
        val product = productRepository.findById(request.productId)
            .orElseThrow { NotFoundException("Product not found with ID: ${request.productId}") }

        val dto = TransactionDto(
            id = 0L,
            totalProducts = request.quantity,
            totalPrice = product.price.multiply(request.quantity.toBigDecimal()),
            type = TransactionType.PURCHASE,
            status = TransactionStatus.PENDING,
            description = request.description,
            updatedAt = LocalDateTime.now(),
            createdAt = LocalDateTime.now(),
            productId = request.productId,
            userId = 1L,
            supplierId = request.supplierId
        )

        return createTransaction(dto, TransactionType.PURCHASE)
    }

    override fun sell(request: SellRequest): Response {
        val product = productRepository.findById(request.productId)
            .orElseThrow { NotFoundException("Product not found with ID: ${request.productId}") }

        val dto = TransactionDto(
            id = 0L,
            totalProducts = request.quantity,
            totalPrice = product.price.multiply(request.quantity.toBigDecimal()),
            type = TransactionType.SELL,
            status = TransactionStatus.PENDING,
            description = request.description,
            updatedAt = LocalDateTime.now(),
            createdAt = LocalDateTime.now(),
            productId = request.productId,
            userId = 1L,
            supplierId = null               // SELL has no supplier
        )

        return createTransaction(dto, TransactionType.SELL)
    }

    override fun returnToSupplier(request: ReturnToSupplierRequest): Response {
        val product = productRepository.findById(request.productId)
            .orElseThrow { NotFoundException("Product not found with ID: ${request.productId}") }

        val dto = TransactionDto(
            id = 0L,
            totalProducts = request.quantity,
            totalPrice = product.price.multiply(request.quantity.toBigDecimal()),
            type = TransactionType.RETURN_TO_SUPPLIER,
            status = TransactionStatus.PENDING,
            description = request.description,
            updatedAt = LocalDateTime.now(),
            createdAt = LocalDateTime.now(),
            productId = request.productId,
            userId = 1L,
            supplierId = request.supplierId
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
        val product = productRepository.findById(
            dto.productId ?: throw NotFoundException("Product ID required")
        ).orElseThrow {
            NotFoundException("Product not found with ID: ${dto.productId}")
        }

        val user = userRepository.findById(
            dto.userId ?: throw NotFoundException("User ID required")
        ).orElseThrow {
            NotFoundException("User not found with ID: ${dto.userId}")
        }


        val supplier = when (type) {
            TransactionType.PURCHASE, TransactionType.RETURN_TO_SUPPLIER -> {
                val sid = dto.supplierId ?: throw NotFoundException("Supplier ID required")
                supplierRepository.findById(sid).orElseThrow {
                    NotFoundException("Supplier not found with ID: $sid")
                }
            }
            TransactionType.SELL -> null
        }

        val now = LocalDateTime.now()
        val tx = Transaction(
            totalProducts = dto.totalProducts,
            totalPrice = dto.totalPrice,
            type = type,
            status = TransactionStatus.PENDING, // or dto.status if you want to honor it
            description = dto.description ?: "",
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
        updatedAt = updatedAt,
        createdAt = createdAt,
        productId = product?.id,
        userId = user?.id,
        supplierId = supplier?.id
    )
}
