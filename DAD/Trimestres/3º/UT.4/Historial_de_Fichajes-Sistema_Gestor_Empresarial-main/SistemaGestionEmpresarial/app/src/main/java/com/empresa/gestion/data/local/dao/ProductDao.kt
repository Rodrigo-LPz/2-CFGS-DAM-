package com.empresa.gestion.data.local.dao

import androidx.room.*
import com.empresa.gestion.data.local.entity.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM products WHERE active = 1 ORDER BY name ASC")
    suspend fun getAllActive(): List<ProductEntity>

    @Query("SELECT * FROM products ORDER BY name ASC")
    suspend fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getById(id: Int): ProductEntity?

    @Query("SELECT * FROM products WHERE sku = :sku")
    suspend fun getBySku(sku: String): ProductEntity?

    @Query("SELECT * FROM products WHERE category = :category AND active = 1")
    suspend fun getByCategory(category: String): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    @Update
    suspend fun update(product: ProductEntity)

    @Delete
    suspend fun delete(product: ProductEntity)

    @Query("DELETE FROM products")
    suspend fun deleteAll()
}