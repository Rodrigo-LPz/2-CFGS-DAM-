package com.empresa.gestion.data.repository

import android.content.Context
import com.empresa.gestion.data.local.AppDatabase
import com.empresa.gestion.data.local.entity.EmployeeEntity
import com.empresa.gestion.data.remote.RetrofitClient
import com.empresa.gestion.data.remote.dto.LoginRequest
import com.empresa.gestion.domain.model.Employee

class AuthRepository(context: Context) {

    private val apiService = RetrofitClient.apiService
    private val employeeDao = AppDatabase.getInstance(context).employeeDao()

    /**
     * Intenta hacer login con las credenciales proporcionadas
     * Guarda el empleado en cache si el login es exitoso
     */
    suspend fun login(employeeCode: String, pin: String): Result<Employee> {
        return try {
            val request = LoginRequest(employeeCode, pin)
            val response = apiService.login(request)

            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!
                val employeeDto = loginResponse.employee

                val employee = Employee(
                    id = employeeDto.id,
                    employeeCode = employeeDto.employeeCode,
                    name = employeeDto.name,
                    email = employeeDto.email,
                    role = employeeDto.role,
                    active = employeeDto.active == 1,
                    createdAt = employeeDto.createdAt
                )

                // Guardar/Actualizar en cache local para uso offline
                val entity = EmployeeEntity(
                    id = employee.id,
                    employeeCode = employee.employeeCode,
                    name = employee.name,
                    email = employee.email,
                    role = employee.role,
                    active = employee.active,
                    createdAt = employee.createdAt
                )
                employeeDao.insert(entity)

                Result.success(employee)
            } else {
                Result.failure(Exception("Código de empleado o PIN incorrecto"))
            }
        } catch (e: Exception) {
            // SI FALLA LA CONEXIÓN (Servidor apagado), buscamos en la base de datos local
            val localEmployee = employeeDao.getByCode(employeeCode)

            if (localEmployee != null) {
                // Si existe localmente, lo dejamos pasar (Modo Offline)
                val employee = Employee(
                    id = localEmployee.id,
                    employeeCode = localEmployee.employeeCode,
                    name = localEmployee.name,
                    email = localEmployee.email,
                    role = localEmployee.role,
                    active = localEmployee.active,
                    createdAt = localEmployee.createdAt
                )
                Result.success(employee)
            } else {
                Result.failure(Exception("Error de conexión y no hay datos locales: ${e.message}"))
            }
        }
    }
}