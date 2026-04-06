package com.empresa.gestion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stock_movements")
data class StockMovementEntity(
    @PrimaryKey
    val id: Int,
    val productId: Int,
    val productName: String,
    val sku: String,
    val employeeId: Int,
    val employeeName: String,
    val movementType: String, // "IN" o "OUT"
    val quantity: Int,
    val reason: String,
    val notes: String?,
    val timestamp: Long
)