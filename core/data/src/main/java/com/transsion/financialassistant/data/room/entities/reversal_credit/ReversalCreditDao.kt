package com.transsion.financialassistant.data.room.entities.reversal_credit

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReversalCreditDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(reversalCredit: ReversalCreditEntity)

    @Delete
    suspend fun delete(reversalCredit: ReversalCreditEntity)

    @Query("SELECT * FROM ReversalCreditEntity")
    fun getAll(): Flow<List<ReversalCreditEntity>>

    @Query("SELECT * FROM ReversalCreditEntity")
    fun getAllPaged(): PagingSource<Int, ReversalCreditEntity>

    @Query("SELECT * FROM ReversalCreditEntity WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    suspend fun getReversalCreditTransactionsByDate(
        startDate: String,
        endDate: String
    ): List<ReversalCreditEntity>


}