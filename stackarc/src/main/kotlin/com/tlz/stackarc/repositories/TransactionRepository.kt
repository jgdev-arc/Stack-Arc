package com.tlz.stackarc.repositories

import com.tlz.stackarc.models.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface TransactionRepository : JpaRepository<com.tlz.stackarc.models.Transaction, Long>, JpaSpecificationExecutor<com.tlz.stackarc.models.Transaction> {

}
