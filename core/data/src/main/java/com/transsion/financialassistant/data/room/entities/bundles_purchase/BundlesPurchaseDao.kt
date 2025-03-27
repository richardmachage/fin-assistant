package com.transsion.financialassistant.data.room.entities.bundles_purchase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BundlesPurchaseDao {
    // create
    @Insert
    suspend fun insert(bundlesPurchaseEntity: BundlesPurchaseEntity)

    // update
    @Update
    suspend fun update(bundlesPurchaseEntity: BundlesPurchaseEntity)

    // delete
    @Delete
    suspend fun delete(bundlesPurchaseEntity: BundlesPurchaseEntity)

    // read

    @Query("SELECT * FROM bundles_purchase")
    fun getAll(): Flow<List<BundlesPurchaseEntity>>

}