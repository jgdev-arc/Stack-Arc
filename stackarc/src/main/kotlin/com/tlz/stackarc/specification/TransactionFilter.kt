package com.tlz.stackarc.specification

import com.tlz.stackarc.enums.TransactionStatus
import com.tlz.stackarc.enums.TransactionType
import com.tlz.stackarc.models.Transaction
import jakarta.persistence.criteria.Expression
import jakarta.persistence.criteria.JoinType
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification

class TransactionFilter {

    fun byFilter(searchValue: String?): Specification<Transaction> {
        return Specification { root, _, cb ->
            if (searchValue.isNullOrBlank()) {
                return@Specification cb.conjunction()
            }

            val searchPattern = "%${searchValue.lowercase()}%"
            val predicates = mutableListOf<Predicate>()

            predicates += cb.like(cb.lower(root.get("description")), searchPattern)

            val statusPath = root.get<Enum<TransactionStatus>>("status").`as`(String::class.java)
            predicates += cb.like(cb.lower(statusPath), searchPattern)

            val typePath = root.get<Enum<TransactionType>>("type").`as`(String::class.java)
            predicates += cb.like(cb.lower(typePath), searchPattern)

            val userJoin = root.join<Any, Any>("user", JoinType.LEFT)
            predicates += cb.like(cb.lower(userJoin.get("name")), searchPattern)
            predicates += cb.like(cb.lower(userJoin.get("email")), searchPattern)
            predicates += cb.like(cb.lower(userJoin.get("phoneNumber")), searchPattern)

            val supplierJoin = root.join<Any, Any>("supplier", JoinType.LEFT)
            predicates += cb.like(cb.lower(supplierJoin.get("name")), searchPattern)
            predicates += cb.like(cb.lower(supplierJoin.get("contactInfo")), searchPattern)

            val productJoin = root.join<Any, Any>("product", JoinType.LEFT)
            predicates += cb.like(cb.lower(productJoin.get("name")), searchPattern)
            predicates += cb.like(cb.lower(productJoin.get("sku")), searchPattern)
            predicates += cb.like(cb.lower(productJoin.get("description")), searchPattern)

            val categoryJoin = productJoin.join<Any, Any>("category", JoinType.LEFT)
            predicates += cb.like(cb.lower(categoryJoin.get("name")), searchPattern)

            return@Specification cb.or(*predicates.toTypedArray())
        }
    }

    fun byMonthAndYear(month: Int, year: Int): Specification<Transaction> {
        return Specification { root, _, cb ->
            val monthExpression =
                cb.function("month", Int::class.java, root.get<java.time.LocalDateTime>("createdAt"))
            val yearExpression: Expression<Int> =
                cb.function("year", Int::class.java, root.get<java.time.LocalDateTime>("createdAt"))

            val monthPredicate = cb.equal(monthExpression, month)
            val yearPredicate = cb.equal(yearExpression, year)

            cb.and(monthPredicate, yearPredicate)
        }
    }
}
