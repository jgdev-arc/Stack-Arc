package com.tlz.stackarc.services

import com.tlz.stackarc.dtos.Response
import com.tlz.stackarc.dtos.TransactionDto

import com.tlz.stackarc.dtos.TransactionRequest

interface TransactionService {

    fun addTransaction(transactionDto: TransactionDto): Response

    fun getAllTransactions(): Response

    fun getTransactionById(id: Long): Response

    fun updateTransaction(id: Long, transactionDto: TransactionDto): Response

    fun deleteTransaction(id: Long): Response

    fun searchTransactions(input: String): Response

    fun purchase(request: TransactionRequest): Response

    fun sell(request: TransactionRequest): Response

    fun returnToSupplier(request: TransactionRequest): Response

    fun getAllTransactionByMonthAndYear(month: Int, year: Int): Response
}
