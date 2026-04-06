package com.empresa.gestion.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("employee")
    val employee: EmployeeDto
)

data class EmployeeDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("employee_code")
    val employeeCode: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("role")
    val role: String,

    @SerializedName("active")
    val active: Int,

    @SerializedName("created_at")
    val createdAt: Long
)