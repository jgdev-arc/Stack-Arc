package com.tlz.stackarc.dtos

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class CategoryDto(
    val id: Long,
    val name: String,
    val products: List<Long> = emptyList()
)