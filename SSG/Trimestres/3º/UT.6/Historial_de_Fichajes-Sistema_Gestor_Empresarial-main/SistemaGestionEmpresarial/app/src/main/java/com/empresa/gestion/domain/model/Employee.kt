package com.empresa.gestion.domain.model

data class Employee(
    val id: Int,
    val employeeCode: String,
    val name: String,
    val email: String,
    val role: String,
    val active: Boolean,
    val createdAt: Long
)