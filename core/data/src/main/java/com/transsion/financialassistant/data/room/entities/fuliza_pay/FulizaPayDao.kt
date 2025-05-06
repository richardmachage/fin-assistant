package com.transsion.financialassistant.data.room.entities.fuliza_pay

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FulizaPayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fulizaPayEntity: FulizaPayEntity)

    @Update
    suspend fun update(fulizaPayEntity: FulizaPayEntity)

    @Delete
    suspend fun delete(fulizaPayEntity: FulizaPayEntity)

    @Query("SELECT * FROM FulizaPayEntity")
    fun getAll(): Flow<List<FulizaPayEntity>>

    @Query("SELECT * FROM FulizaPayEntity WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    suspend fun getPayFulizaTransactionsByDate(
        startDate: String,
        endDate: String
    ): List<FulizaPayEntity>
}