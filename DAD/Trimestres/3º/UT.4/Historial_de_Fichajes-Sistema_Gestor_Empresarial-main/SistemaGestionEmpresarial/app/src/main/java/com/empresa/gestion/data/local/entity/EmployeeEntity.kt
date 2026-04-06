package com.empresa.gestion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employees")
data class EmployeeEntity(
    @PrimaryKey
    val id: Int,
    val employeeCode: String,
    val name: String,
    val email: String,
    val role: String,
    val active: Boolean,
    val createdAt: Long
)