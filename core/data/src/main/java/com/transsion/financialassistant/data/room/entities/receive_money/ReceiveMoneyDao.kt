package com.transsion.financialassistant.data.room.entities.receive_money

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceiveMoneyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(receiveMoneyEntity: ReceiveMoneyEntity)

    @Update
    suspend fun update(receiveMoneyEntity: ReceiveMoneyEntity)

    @Delete
    suspend fun delete(receiveMoneyEntity: ReceiveMoneyEntity)

    @Query("SELECT * FROM ReceiveMoneyEntity")
    fun getAll(): Flow<List<ReceiveMoneyEntity>>

    @Query("SELECT * FROM ReceiveMoneyEntity WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    suspend fun getReceiveMoneyTransactionsByDate(startDate: String, endDate: String): List<ReceiveMoneyEntity>
}