package com.empresa.gestion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
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