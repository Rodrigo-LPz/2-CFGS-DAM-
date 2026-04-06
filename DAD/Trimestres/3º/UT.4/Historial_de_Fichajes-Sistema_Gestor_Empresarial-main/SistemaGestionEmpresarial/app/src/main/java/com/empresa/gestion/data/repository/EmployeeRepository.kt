package com.empresa.gestion.data.repository

import android.content.Context
import com.empresa.gestion.data.local.AppDatabase
import com.empresa.gestion.data.local.entity.EmployeeEntity
import com.empresa.gestion.data.remote.RetrofitClient
import com.empresa.gestion.data.remote.dto.EmployeeDto
import com.empresa.gestion.domain.model.Employee

class EmployeeRepository(context: Context) {

    private val apiService = RetrofitClient.apiService
    private val employeeDao = AppDatabase.getInstance(context).employeeDao()

    /**
     * Obtiene la lista de empleados
     * Intenta desde la API, si falla usa cache local
     */
    suspend fun getEmployees(activeOnly: Boolean = true): Result<List<Employee>> {
        return try {
            val response = apiService.getEmployees(activeOnly)

            if (response.isSuccessful && response.body() != null) {
                val employees = response.body()!!.map { dto ->
                    Employee(
                        id = dto.id,
                        employeeCode = dto.employeeCode,
                        name = dto.name,
                        email = dto.email,
                        role = dto.role,
                        active = dto.active == 1,
                        createdAt = dto.createdAt
                    )
                }

                // Guardar en cache local
                val entities = employees.map { employee ->
                    EmployeeEntity(
                        id = employee.id,
                        employeeCode = employee.employeeCode,
                        name = employee.name,
                        email = employee.email,
                        role = employee.role,
                        active = employee.active,
                        createdAt = employee.createdAt
                    )
                }
                employeeDao.deleteAll() // Limpiar cache antiguo
                employeeDao.insertAll(entities)

                Result.success(employees)
            } else {
                // Si falla la API, intentar desde cache
                getFromCache(activeOnly)
            }
        } catch (e: Exception) {
            // Si hay error de conexión, usar cache
            getFromCache(activeOnly)
        }
    }

    /**
     * Crea un nuevo empleado
     */
    suspend fun createEmployee(
        employeeCode: String,
        name: String,
        email: String,
        pin: String,
        role: String
    ): Result<Employee> {
        return try {
            // Crear el DTO de request
            val request = mapOf(
                "employee_code" to employeeCode,
                "name" to name,
                "email" to email,
                "pin" to pin,
                "role" to role
            )

            val response = apiService.createEmployee(request)

            if (response.isSuccessful && response.body() != null) {
                val dto = response.body()!!
                val employee = Employee(
                    id = dto.id,
                    employeeCode = dto.employeeCode,
                    name = dto.name,
                    email = dto.email,
                    role = dto.role,
                    active = dto.active == 1,
                    createdAt = dto.createdAt
                )

                // Guardar en cache local
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
                val errorMsg = when (response.code()) {
                    400 -> "Datos inválidos o empleado duplicado"
                    else -> "Error al crear empleado: ${response.code()}"
                }
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }

    /**
     * Elimina (desactiva) un empleado
     */
    suspend fun deleteEmployee(employeeId: Int): Result<Unit> {
        return try {
            val response = apiService.deleteEmployee(employeeId)

            if (response.isSuccessful) {
                // Eliminar del cache local también
                val entity = employeeDao.getById(employeeId)
                if (entity != null) {
                    employeeDao.delete(entity)
                }

                Result.success(Unit)
            } else {
                val errorMsg = when (response.code()) {
                    404 -> "Empleado no encontrado"
                    else -> "Error al eliminar empleado: ${response.code()}"
                }
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }

    /**
     * Obtiene empleados desde el cache local
     */
    private suspend fun getFromCache(activeOnly: Boolean): Result<List<Employee>> {
        return try {
            val entities = if (activeOnly) {
                employeeDao.getAllActive()
            } else {
                employeeDao.getAll()
            }

            val employees = entities.map { entity ->
                Employee(
                    id = entity.id,
                    employeeCode = entity.employeeCode,
                    name = entity.name,
                    email = entity.email,
                    role = entity.role,
                    active = entity.active,
                    createdAt = entity.createdAt
                )
            }

            if (employees.isEmpty()) {
                Result.failure(Exception("No hay datos en cache. Verifica tu conexión."))
            } else {
                Result.success(employees)
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error al leer cache: ${e.message}"))
        }
    }
}