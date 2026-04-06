package com.empresa.gestion.data.local.dao

import androidx.room.*
import com.empresa.gestion.data.local.entity.StockMovementEntity

@Dao
interface StockMovementDao {

    @Query("SELECT * FROM stock_movements ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getAll(limit: Int = 100): List<StockMovementEntity>

    @Query("SELECT * FROM stock_movements WHERE productId = :productId ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getByProductId(productId: Int, limit: Int = 100): List<StockMovementEntity>

    @Query("SELECT * FROM stock_movements WHERE employeeId = :employeeId ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getByEmployeeId(employeeId: Int, limit: Int = 100): List<StockMovementEntity>

    @Query("SELECT * FROM stock_movements WHERE id = :id")
    suspend fun getById(id: Int): StockMovementEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movement: StockMovementEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movements: List<StockMovementEntity>)

    @Update
    suspend fun update(movement: StockMovementEntity)

    @Delete
    suspend fun delete(movement: StockMovementEntity)

    @Query("DELETE FROM stock_movements")
    suspend fun deleteAll()
}