package com.tlz.stackarc.repositories

import com.tlz.stackarc.models.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TransactionRepository : JpaRepository<Transaction, Long> {

    fun findByDescriptionContaining(description: String): List<Transaction>

    @Query("SELECT t FROM Transaction t WHERE MONTH(t.createdAt) = :month AND YEAR(t.createdAt) = :year")
    fun findAllByMonthAndYear(@Param("month") month: Int, @Param("year") year: Int): List<Transaction>
}
