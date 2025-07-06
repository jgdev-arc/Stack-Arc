package com.tlz.stackarc.controllers

import com.tlz.stackarc.dtos.Response
import com.tlz.stackarc.dtos.TransactionDto
import com.tlz.stackarc.dtos.TransactionRequest
import com.tlz.stackarc.services.TransactionService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/transactions")
class TransactionController(
    private val transactionService: TransactionService
) {

    @PostMapping("/purchase")
    fun purchase(@RequestBody @Valid request: TransactionRequest): ResponseEntity<Response> {
        val response = transactionService.purchase(request)
        return ResponseEntity.status(response.status).body(response)
    }

    @PostMapping("/sell")
    fun sell(@RequestBody @Valid request: TransactionRequest): ResponseEntity<Response> {
        val response = transactionService.sell(request)
        return ResponseEntity.status(response.status).body(response)
    }

    @PostMapping("/return")
    fun returnToSupplier(@RequestBody @Valid request: TransactionRequest): ResponseEntity<Response> {
        val response = transactionService.returnToSupplier(request)
        return ResponseEntity.status(response.status).body(response)
    }

    @GetMapping("/month-year")
    fun getAllByMonthAndYear(
        @RequestParam month: Int,
        @RequestParam year: Int
    ): ResponseEntity<Response> {
        val response = transactionService.getAllTransactionByMonthAndYear(month, year)
        return ResponseEntity.status(response.status).body(response)
    }

    @GetMapping("/all")
    fun getAllTransactions(): ResponseEntity<Response> {
        val response = transactionService.getAllTransactions()
        return ResponseEntity.status(response.status).body(response)
    }


    @GetMapping("/{id}")
    fun getTransactionById(@PathVariable id: Long): ResponseEntity<Response> {
        val response = transactionService.getTransactionById(id)
        return ResponseEntity.status(response.status).body(response)
    }

    @PutMapping("/update/{id}")
    fun updateTransaction(
        @PathVariable id: Long,
        @RequestBody transactionDto: TransactionDto
    ): ResponseEntity<Response> {
        val response = transactionService.updateTransaction(id, transactionDto)
        return ResponseEntity.status(response.status).body(response)
    }


}

