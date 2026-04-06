package com.empresa.gestion.domain.model

data class StockMovement(
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