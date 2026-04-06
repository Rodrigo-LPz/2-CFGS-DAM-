package com.empresa.gestion.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.empresa.gestion.data.local.dao.EmployeeDao
import com.empresa.gestion.data.local.dao.ProductDao
import com.empresa.gestion.data.local.dao.PunchDao
import com.empresa.gestion.data.local.dao.StockMovementDao
import com.empresa.gestion.data.local.entity.EmployeeEntity
import com.empresa.gestion.data.local.entity.ProductEntity
import com.empresa.gestion.data.local.entity.PunchEntity
import com.empresa.gestion.data.local.entity.StockMovementEntity

@Database(
    entities = [
        EmployeeEntity::class,
        PunchEntity::class,
        ProductEntity::class,
        StockMovementEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // DAOs
    abstract fun employeeDao(): EmployeeDao
    abstract fun punchDao(): PunchDao
    abstract fun productDao(): ProductDao
    abstract fun stockMovementDao(): StockMovementDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gestion_empresa_database"
                )
                    .fallbackToDestructiveMigration() // En producción usar migrations
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}