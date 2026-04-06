package com.empresa.gestion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "punches")
data class PunchEntity(
    @PrimaryKey
    val id: Int,
    val employeeId: Int,
    val employeeName: String,
    val employeeCode: String,
    val punchType: String, // "IN" o "OUT"
    val timestamp: Long,
    val location: String?,
    val notes: String?
)