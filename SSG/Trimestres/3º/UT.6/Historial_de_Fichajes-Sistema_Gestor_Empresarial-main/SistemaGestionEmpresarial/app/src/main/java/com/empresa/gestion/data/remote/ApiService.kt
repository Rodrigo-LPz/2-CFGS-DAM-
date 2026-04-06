package com.empresa.gestion.data.remote

import com.empresa.gestion.data.remote.dto.EmployeeDto
import com.empresa.gestion.data.remote.dto.LoginRequest
import com.empresa.gestion.data.remote.dto.LoginResponse
import com.empresa.gestion.data.remote.dto.PunchRequest
import com.empresa.gestion.data.remote.dto.PunchResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    // ============================================
    // AUTENTICACIÓN
    // ============================================

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    // ============================================
    // FICHAJES
    // ============================================

    @POST("punches")
    suspend fun createPunch(
        @Body request: PunchRequest
    ): Response<PunchResponse>

    @GET("punches")
    suspend fun getPunches(
        @Query("employee_id") employeeId: Int? = null,
        @Query("limit") limit: Int = 100
    ): Response<List<PunchResponse>>

    @GET("punches/employee/{employee_code}")
    suspend fun getPunchesByCode(
        @retrofit2.http.Path("employee_code") employeeCode: String,
        @Query("limit") limit: Int = 50
    ): Response<List<PunchResponse>>

    // ============================================
    // EMPLEADOS
    // ============================================

    @GET("employees")
    suspend fun getEmployees(
        @Query("active_only") activeOnly: Boolean = true
    ): Response<List<EmployeeDto>>

    @POST("employees")
    suspend fun createEmployee(
        @Body request: Map<String, String>
    ): Response<EmployeeDto>

    @retrofit2.http.DELETE("employees/{employee_id}")
    suspend fun deleteEmployee(
        @retrofit2.http.Path("employee_id") employeeId: Int
    ): Response<Unit>
}