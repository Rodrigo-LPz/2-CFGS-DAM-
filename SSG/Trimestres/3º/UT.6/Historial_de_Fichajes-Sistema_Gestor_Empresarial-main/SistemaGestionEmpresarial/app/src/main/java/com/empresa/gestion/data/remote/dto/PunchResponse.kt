package com.empresa.gestion.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PunchResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("employee_id")
    val employeeId: Int,

    @SerializedName("employee_name")
    val employeeName: String,

    @SerializedName("employee_code")
    val employeeCode: String,

    @SerializedName("punch_type")
    val punchType: String,

    @SerializedName("timestamp")
    val timestamp: Long,

    @SerializedName("location")
    val location: String?,

    @SerializedName("notes")
    val notes: String?
)