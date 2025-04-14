package com.transsion.financialassistant.data.room.views.unified_transactions

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.transsion.financialassistant.data.room.db.UnifiedTransaction

@Dao
interface UnifiedTransactionsDao {
    @Query("SELECT * FROM UnifiedTransaction WHERE transactionType = 'IN' ORDER BY date DESC, time DESC")
    fun getAllTransactionsIn(): PagingSource<Int, UnifiedTransaction>

    @Query("SELECT * FROM UnifiedTransaction WHERE transactionType = 'IN' ORDER BY date ASC, time ASC")
    fun getAllTransactionsInReverse(): PagingSource<Int, UnifiedTransaction>

    @Query("SELECT * FROM UnifiedTransaction WHERE transactionType = 'OUT' ORDER BY date DESC, time DESC")
    fun getAllTransactionsOut(): PagingSource<Int, UnifiedTransaction>

    @Query("SELECT * FROM UnifiedTransaction WHERE transactionType = 'OUT' ORDER BY date ASC, time ASC")
    fun getAllTransactionsOutReverse(): PagingSource<Int, UnifiedTransaction>

    @Query("SELECT * FROM UnifiedTransaction WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC, time DESC")
    fun getAllTransactionsForDate(
        startDate: String,
        endDate: String
    ): PagingSource<Int, UnifiedTransaction>


}