package com.transsion.financialassistant.data.room.entities.bundles_purchase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BundlesPurchaseDao {
    // create
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bundlesPurchaseEntity: BundlesPurchaseEntity)

    // update
    @Update
    suspend fun update(bundlesPurchaseEntity: BundlesPurchaseEntity)

    // delete
    @Delete
    suspend fun delete(bundlesPurchaseEntity: BundlesPurchaseEntity)

    // read

    @Query("SELECT * FROM BundlesPurchaseEntity")
    suspend fun getAll(): List<BundlesPurchaseEntity>

    @Query("SELECT * FROM BundlesPurchaseEntity WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    suspend fun getBundlesPurchaseTransactionsByDate(startDate: String, endDate: String): List<BundlesPurchaseEntity>
}