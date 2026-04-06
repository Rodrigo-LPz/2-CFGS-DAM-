package com.empresa.gestion.domain.model

data class Punch(
    val id: Int,
    val employeeId: Int,
    val employeeName: String,
    val employeeCode: String,
    val punchType: String, // "IN" o "OUT"
    val timestamp: Long,
    val location: String?,
    val notes: String?
)