package com.tlz.stackarc.services

import com.tlz.stackarc.dtos.*

interface TransactionService {

    fun addTransaction(transactionDto: TransactionDto): Response

    fun getAllTransactions(trim: String): Response

    fun getTransactionById(id: Long): Response

    fun updateTransaction(id: Long, transactionDto: TransactionDto): Response

    fun deleteTransaction(id: Long): Response

    fun searchTransactions(input: String): Response

    fun purchase(request: PurchaseRequest): Response

    fun sell(request: SellRequest): Response

    fun returnToSupplier(request: ReturnToSupplierRequest): Response

    fun getAllTransactionByMonthAndYear(month: Int, year: Int): Response
}
