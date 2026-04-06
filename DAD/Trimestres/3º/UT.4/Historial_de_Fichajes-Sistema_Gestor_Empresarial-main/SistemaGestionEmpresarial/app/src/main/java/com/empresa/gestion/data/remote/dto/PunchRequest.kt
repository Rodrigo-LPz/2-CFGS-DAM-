package com.empresa.gestion.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PunchRequest(
    @SerializedName("employee_code")
    val employeeCode: String,

    @SerializedName("pin")
    val pin: String,

    @SerializedName("punch_type")
    val punchType: String, // "IN" o "OUT"

    @SerializedName("location")
    val location: String? = null,

    @SerializedName("notes")
    val notes: String? = null
)