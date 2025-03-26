package com.transsion.financialassistant.data.room.entities.receive_money

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceiveMoneyDao {

    @Insert
    suspend fun insert(receiveMoneyEntity: ReceiveMoneyEntity)

    @Update
    suspend fun update(receiveMoneyEntity: ReceiveMoneyEntity)

    @Delete
    suspend fun delete(receiveMoneyEntity: ReceiveMoneyEntity)

    @Query("SELECT * FROM ReceiveMoneyEntity")
    fun getAll(): Flow<List<ReceiveMoneyEntity>>




}