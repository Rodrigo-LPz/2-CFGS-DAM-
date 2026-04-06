package com.empresa.gestion.data.local.dao

import androidx.room.*
import com.empresa.gestion.data.local.entity.PunchEntity

@Dao
interface PunchDao {

    @Query("SELECT * FROM punches ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getAll(limit: Int = 100): List<PunchEntity>

    @Query("SELECT * FROM punches WHERE employeeId = :employeeId ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getByEmployeeId(employeeId: Int, limit: Int = 100): List<PunchEntity>

    @Query("SELECT * FROM punches WHERE id = :id")
    suspend fun getById(id: Int): PunchEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(punch: PunchEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(punches: List<PunchEntity>)

    @Update
    suspend fun update(punch: PunchEntity)

    @Delete
    suspend fun delete(punch: PunchEntity)

    @Query("DELETE FROM punches")
    suspend fun deleteAll()
}