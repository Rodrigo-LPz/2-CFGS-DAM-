package com.empresa.gestion.data.local.dao

import androidx.room.*
import com.empresa.gestion.data.local.entity.EmployeeEntity

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employees WHERE active = 1 ORDER BY name ASC")
    suspend fun getAllActive(): List<EmployeeEntity>

    @Query("SELECT * FROM employees ORDER BY name ASC")
    suspend fun getAll(): List<EmployeeEntity>

    @Query("SELECT * FROM employees WHERE id = :id")
    suspend fun getById(id: Int): EmployeeEntity?

    @Query("SELECT * FROM employees WHERE employeeCode = :employeeCode")
    suspend fun getByCode(employeeCode: String): EmployeeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(employee: EmployeeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(employees: List<EmployeeEntity>)

    @Update
    suspend fun update(employee: EmployeeEntity)

    @Delete
    suspend fun delete(employee: EmployeeEntity)

    @Query("DELETE FROM employees")
    suspend fun deleteAll()
}