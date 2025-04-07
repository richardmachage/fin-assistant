package com.transsion.financialassistant.data.room.entities.deposit

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DepositMoneyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(depositMoneyEntity: DepositMoneyEntity)

    @Update
    suspend fun updateDepositMoneyTransaction(depositMoneyEntity: DepositMoneyEntity)

    @Query("SELECT * FROM DepositMoneyEntity WHERE transactionCode = :transactionCode")
    suspend fun getDepositMoneyTransactionByCode(transactionCode: String): DepositMoneyEntity?

    @Query("SELECT * FROM DepositMoneyEntity WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    suspend fun getDepositMoneyTransactionsByDate(startDate: String, endDate: String): List<DepositMoneyEntity>

    @Query("SELECT * FROM DepositMoneyEntity")
    fun getAllDepositMoneyTransactions(): Flow<List<DepositMoneyEntity>>

    @Delete
    suspend fun deleteDepositMoneyTransaction(depositMoneyEntity: DepositMoneyEntity)



}