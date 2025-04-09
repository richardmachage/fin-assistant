package com.transsion.financialassistant.data.room.entities.send_money

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface SendMoneyDao {
    //create
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sendMoneyEntity: SendMoneyEntity)

    //read
    @Query("SELECT * FROM SendMoneyEntity")
    fun getAll(): Flow<List<SendMoneyEntity>>

    //update
    @Update
    suspend fun update(sendMoneyEntity: SendMoneyEntity)

    //delete
    @Delete
    suspend fun delete(sendMoneyEntity: SendMoneyEntity)

    @Query("SELECT * FROM SendMoneyEntity WHERE date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    suspend fun getSendMoneyTransactionsByDate(startDate: String, endDate: String): List<SendMoneyEntity>
}