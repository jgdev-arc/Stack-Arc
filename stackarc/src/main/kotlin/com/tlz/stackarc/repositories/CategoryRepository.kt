package com.tlz.stackarc.repositories


import com.tlz.stackarc.models.Category
import org.springframework.data.jpa.repository.JpaRepository
import com.tlz.stackarc.models.Product

interface CategoryRepository : JpaRepository<com.tlz.stackarc.models.Category, Long> {

}
