package com.empresa.gestion.data.repository

import android.content.Context
import com.empresa.gestion.data.local.AppDatabase
import com.empresa.gestion.data.local.entity.PunchEntity
import com.empresa.gestion.data.remote.RetrofitClient
import com.empresa.gestion.data.remote.dto.PunchRequest
import com.empresa.gestion.domain.model.Punch

class PunchRepository(context: Context) {

    private val apiService = RetrofitClient.apiService
    private val punchDao = AppDatabase.getInstance(context).punchDao()

    /**
     * Registra un fichaje (entrada o salida)
     */
    suspend fun createPunch(
        employeeCode: String,
        pin: String,
        punchType: String,
        location: String? = null,
        notes: String? = null
    ): Result<Punch> {
        return try {
            val request = PunchRequest(
                employeeCode = employeeCode,
                pin = pin,
                punchType = punchType,
                location = location,
                notes = notes
            )

            val response = apiService.createPunch(request)

            if (response.isSuccessful && response.body() != null) {
                val punchDto = response.body()!!

                // Convertir DTO a modelo de dominio
                val punch = Punch(
                    id = punchDto.id,
                    employeeId = punchDto.employeeId,
                    employeeName = punchDto.employeeName,
                    employeeCode = punchDto.employeeCode,
                    punchType = punchDto.punchType,
                    timestamp = punchDto.timestamp,
                    location = punchDto.location,
                    notes = punchDto.notes
                )

                // Guardar en cache local
                val entity = PunchEntity(
                    id = punch.id,
                    employeeId = punch.employeeId,
                    employeeName = punch.employeeName,
                    employeeCode = punch.employeeCode,
                    punchType = punch.punchType,
                    timestamp = punch.timestamp,
                    location = punch.location,
                    notes = punch.notes
                )
                punchDao.insert(entity)

                Result.success(punch)
            } else {
                val errorMsg = when (response.code()) {
                    401 -> "Código de empleado o PIN incorrecto"
                    400 -> "Datos inválidos"
                    else -> "Error al registrar fichaje: ${response.code()}"
                }
                Result.failure(Exception(errorMsg))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error de conexión: ${e.message}"))
        }
    }

    /**
     * Obtiene el historial de fichajes
     * Intenta desde la API, si falla usa cache local
     */
    suspend fun getPunches(employeeId: Int? = null, limit: Int = 100): Result<List<Punch>> {
        return try {
            // Intentar obtener desde la API
            val response = apiService.getPunches(employeeId, limit)

            if (response.isSuccessful && response.body() != null) {
                val punches = response.body()!!.map { dto ->
                    Punch(
                        id = dto.id,
                        employeeId = dto.employeeId,
                        employeeName = dto.employeeName,
                        employeeCode = dto.employeeCode,
                        punchType = dto.punchType,
                        timestamp = dto.timestamp,
                        location = dto.location,
                        notes = dto.notes
                    )
                }

                // Guardar en cache local
                val entities = punches.map { punch ->
                    PunchEntity(
                        id = punch.id,
                        employeeId = punch.employeeId,
                        employeeName = punch.employeeName,
                        employeeCode = punch.employeeCode,
                        punchType = punch.punchType,
                        timestamp = punch.timestamp,
                        location = punch.location,
                        notes = punch.notes
                    )
                }
                punchDao.deleteAll() // Limpiar cache antiguo
                punchDao.insertAll(entities)

                Result.success(punches)
            } else {
                // Si falla la API, intentar desde cache
                getFromCache(employeeId, limit)
            }
        } catch (e: Exception) {
            // Si hay error de conexión, usar cache
            getFromCache(employeeId, limit)
        }
    }

    /**
     * Obtiene fichajes desde el cache local
     */
    private suspend fun getFromCache(employeeId: Int?, limit: Int): Result<List<Punch>> {
        return try {
            val entities = if (employeeId != null) {
                punchDao.getByEmployeeId(employeeId, limit)
            } else {
                punchDao.getAll(limit)
            }

            val punches = entities.map { entity ->
                Punch(
                    id = entity.id,
                    employeeId = entity.employeeId,
                    employeeName = entity.employeeName,
                    employeeCode = entity.employeeCode,
                    punchType = entity.punchType,
                    timestamp = entity.timestamp,
                    location = entity.location,
                    notes = entity.notes
                )
            }

            if (punches.isEmpty()) {
                Result.failure(Exception("No hay datos en cache. Verifica tu conexión."))
            } else {
                Result.success(punches)
            }
        } catch (e: Exception) {
            Result.failure(Exception("Error al leer cache: ${e.message}"))
        }
    }
}