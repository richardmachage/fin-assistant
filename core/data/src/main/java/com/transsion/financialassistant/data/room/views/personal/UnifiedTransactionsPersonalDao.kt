package com.transsion.financialassistant.data.room.views.personal

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query

@Dao
interface UnifiedTransactionsPersonalDao {

    @Query("SELECT * FROM UnifiedTransactionPersonal ORDER BY date DESC, time DESC")
    fun getAllTransactions(): PagingSource<Int, UnifiedTransactionPersonal>

    @Query("SELECT * FROM UnifiedTransactionPersonal ORDER BY date ASC, time ASC")
    fun getAllTransactionsReverse(): PagingSource<Int, UnifiedTransactionPersonal>

    @Query("SELECT * FROM UnifiedTransactionPersonal WHERE transactionCategory = 'IN' ORDER BY date DESC, time DESC")
    fun getAllTransactionsIn(): PagingSource<Int, UnifiedTransactionPersonal>

    @Query("SELECT * FROM UnifiedTransactionPersonal WHERE transactionCategory = 'IN' AND date BETWEEN :startDate AND :endDate ORDER BY date DESC, time DESC")
    fun getAllTransactionsInForDate(
        startDate: String,
        endDate: String
    ): PagingSource<Int, UnifiedTransactionPersonal>

    @Query("SELECT * FROM UnifiedTransactionPersonal WHERE transactionCategory = 'IN' ORDER BY date ASC, time ASC")
    fun getAllTransactionsInReverse(): PagingSource<Int, UnifiedTransactionPersonal>

    @Query("SELECT * FROM UnifiedTransactionPersonal WHERE transactionCategory = 'OUT' ORDER BY date DESC, time DESC")
    fun getAllTransactionsOut(): PagingSource<Int, UnifiedTransactionPersonal>

    @Query("SELECT * FROM UnifiedTransactionPersonal WHERE transactionCategory = 'OUT' AND date BETWEEN :startDate AND :endDate ORDER BY date DESC, time DESC")
    fun getAllTransactionsOutForDate(
        startDate: String,
        endDate: String
    ): PagingSource<Int, UnifiedTransactionPersonal>

    @Query("SELECT * FROM UnifiedTransactionPersonal WHERE transactionCategory = 'OUT' ORDER BY date ASC, time ASC")
    fun getAllTransactionsOutReverse(): PagingSource<Int, UnifiedTransactionPersonal>

    @Query("SELECT * FROM UnifiedTransactionPersonal WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC, time DESC")
    fun getAllTransactionsForDate(
        startDate: String,
        endDate: String
    ): PagingSource<Int, UnifiedTransactionPersonal>


}