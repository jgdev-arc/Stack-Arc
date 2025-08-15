package com.tlz.stackarc.controllers

import com.tlz.stackarc.dtos.*
import com.tlz.stackarc.services.TransactionService
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/api/transactions", produces = [MediaType.APPLICATION_JSON_VALUE])
class TransactionController(
    private val transactionService: TransactionService
) {

    @PostMapping(
        "/purchase",
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun purchase(@RequestBody @Valid request: PurchaseRequest): ResponseEntity<Response> {
        val response = transactionService.purchase(request)
        return ResponseEntity.status(response.status).body(response)
    }

    @PostMapping(
        "/sell",
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun sell(@RequestBody @Valid request: SellRequest): ResponseEntity<Response> {
        val response = transactionService.sell(request)
        return ResponseEntity.status(response.status).body(response)
    }

    @PostMapping("/return-to-supplier")
    fun returnToSupplier(
        @RequestBody @Valid request: ReturnToSupplierRequest
    ): ResponseEntity<Response> {
        val response = transactionService.returnToSupplier(request)
        return ResponseEntity.status(response.status).body(response)
    }


    @GetMapping("/month-year")
    fun getAllByMonthAndYear(
        @RequestParam month: Int,
        @RequestParam year: Int
    ): ResponseEntity<Response> {
        require(month in 1..12) { "month must be 1..12" }
        require(year in 1970..9999) { "year must be reasonable" }
        val response = transactionService.getAllTransactionByMonthAndYear(month, year)
        return ResponseEntity.status(response.status).body(response)
    }


    @GetMapping
    fun getAllTransactions(@RequestParam(required = false, defaultValue = "") q: String): ResponseEntity<Response> {
        val response = transactionService.getAllTransactions(q.trim())
        return ResponseEntity.status(response.status).body(response)
    }

    @GetMapping("/{id}")
    fun getTransactionById(@PathVariable id: Long): ResponseEntity<Response> {
        val response = transactionService.getTransactionById(id)
        return ResponseEntity.status(response.status).body(response)
    }

    @PutMapping(
        "/{id}",
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateTransaction(
        @PathVariable id: Long,
        @RequestBody @Valid transactionDto: TransactionDto
    ): ResponseEntity<Response> {
        val response = transactionService.updateTransaction(id, transactionDto)
        return ResponseEntity.status(response.status).body(response)
    }
}
