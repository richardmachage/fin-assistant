package com.transsion.financialassistant.data.room.entities.reversal_debit

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReversalDebitDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(reversalDebit: ReversalDebitEntity)

    @Delete
    suspend fun delete(reversalDebit: ReversalDebitEntity)

    @Query("SELECT * FROM ReversalDebitEntity")
    fun getAll(): Flow<List<ReversalDebitEntity>>

    @Query("SELECT * FROM ReversalDebitEntity WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    suspend fun getReversalDebitTransactionsByDate(
        startDate: String,
        endDate: String
    ): List<ReversalDebitEntity>
}