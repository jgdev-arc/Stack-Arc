package com.tlz.Stack_Arc.repositories

import com.tlz.Stack_Arc.models.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface TransactionRepository : JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

}
