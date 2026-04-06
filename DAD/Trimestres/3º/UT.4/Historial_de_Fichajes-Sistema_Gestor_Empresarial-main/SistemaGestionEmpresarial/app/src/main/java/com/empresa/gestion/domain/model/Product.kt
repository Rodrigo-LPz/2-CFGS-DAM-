package com.empresa.gestion.domain.model

data class Product(
    val id: Int,
    val sku: String,
    val name: String,
    val description: String?,
    val category: String,
    val price: Double,
    val stock: Int,
    val minStock: Int,
    val active: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)