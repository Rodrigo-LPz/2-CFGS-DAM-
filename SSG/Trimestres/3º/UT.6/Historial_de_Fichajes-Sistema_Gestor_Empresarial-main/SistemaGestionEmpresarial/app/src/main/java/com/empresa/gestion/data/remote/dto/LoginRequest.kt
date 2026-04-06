package com.empresa.gestion.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("employee_code")
    val employeeCode: String,

    @SerializedName("pin")
    val pin: String
)